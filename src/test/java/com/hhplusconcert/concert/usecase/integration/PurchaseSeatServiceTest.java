package com.hhplusconcert.concert.usecase.integration;

import com.hhplusconcert.concert.repository.PaymentRepository;
import com.hhplusconcert.concert.repository.ReservationRepository;
import com.hhplusconcert.concert.repository.domain.Payment;
import com.hhplusconcert.concert.repository.domain.Reservation;
import com.hhplusconcert.concert.repository.domain.vo.PaymentStatus;
import com.hhplusconcert.concert.repository.domain.vo.ReservationStatus;
import com.hhplusconcert.concert.usecase.PurchaseSeatService;
import com.hhplusconcert.shared.error.ApplicationException;
import com.hhplusconcert.user.repository.UserRepository;
import com.hhplusconcert.user.repository.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@Rollback
public class PurchaseSeatServiceTest {

    @Autowired
    private PurchaseSeatService purchaseSeatService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;
    private Reservation testReservation;

    @BeforeEach
    public void setup() {
        // 사용자 생성
        testUser = new User(null, "testuser", 1000);  // 사용자 초기 금액 1000
        userRepository.save(testUser);

        // 예약 생성
        testReservation = new Reservation(null, testUser.getId(), 1L, 1L, null, ReservationStatus.RESERVED, LocalDateTime.now());  // 결제 가능한 상태의 예약
        reservationRepository.save(testReservation);
    }

    @Test
    public void testExecute_Success() {
        // given
        PurchaseSeatService.Input input = new PurchaseSeatService.Input(500);

        // when
        PurchaseSeatService.Output result = purchaseSeatService.execute(testReservation.getId(), testUser.getId(), input);

        // then
        assertThat(result.getPurchaseNumber()).isNotNull();

        Payment payment = paymentRepository.findById(result.getPurchaseNumber());
        assertThat(payment.getPrice()).isEqualTo(500);
        assertThat(payment.getStatus()).isEqualTo(PaymentStatus.PROGRESS);

        Reservation updatedReservation = reservationRepository.findById(testReservation.getId());
        assertThat(updatedReservation.getPaymentId()).isEqualTo(payment.getId());

        User updatedUser = userRepository.findById(testUser.getId());
        assertThat(updatedUser.getAmount()).isEqualTo(500);  // 결제 후 500 남음
    }

    @Test
    public void testExecute_InvalidRequest() {
        // given
        testReservation.setStatus(ReservationStatus.PAID);  // 결제 불가능 상태로 변경
        reservationRepository.save(testReservation);
        PurchaseSeatService.Input input = new PurchaseSeatService.Input(500);

        // when & then
        assertThrows(ApplicationException.class, () ->
                purchaseSeatService.execute(testReservation.getId(), testUser.getId(), input));
    }

    @Test
    public void testExecute_InsufficientBalance() {
        // given
        PurchaseSeatService.Input input = new PurchaseSeatService.Input(1500);  // 사용자의 금액을 초과하는 결제 시도

        // when & then
        assertThrows(ApplicationException.class, () ->
                purchaseSeatService.execute(testReservation.getId(), testUser.getId(), input));
    }
}
