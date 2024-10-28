package com.hhplusconcert.concert.repository.domain;

import com.hhplusconcert.concert.repository.domain.vo.ReservationStatus;
import com.hhplusconcert.shared.error.ApplicationException;
import com.hhplusconcert.shared.error.ErrorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationTest {

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        // 각 테스트마다 새로운 Reservation 인스턴스를 생성합니다.
        reservation = new Reservation(1L, 1L, 1L, 1L, null, ReservationStatus.TEMP_RESERVED, LocalDateTime.now());
    }

    @Test
    @DisplayName("TEMP_RESERVED 상태일 때 isPayable 호출 시 true 반환")
    void isPayable_TempReservedStatus_ReturnsTrue() {
        // 상태 확인
        assertThat(reservation.isPayable()).isTrue();
    }

    @Test
    @DisplayName("RESERVED 상태일 때 isPayable 호출 시 true 반환")
    void isPayable_ReservedStatus_ReturnsTrue() {
        reservation.setStatus(ReservationStatus.RESERVED);
        assertThat(reservation.isPayable()).isTrue();
    }

    @Test
    @DisplayName("PAID 상태일 때 isPayable 호출 시 예외 발생")
    void isPayable_PaidStatus_ThrowsException() {
        reservation.setStatus(ReservationStatus.PAID);
        assertThatThrownBy(reservation::isPayable)
                .isInstanceOf(ApplicationException.class)
                .hasMessage(ErrorType.Concert.RESERVATION_ALREADY_PAID.getMessage());
    }

    @Test
    @DisplayName("CANCELED 상태일 때 isPayable 호출 시 예외 발생")
    void isPayable_CanceledStatus_ThrowsException() {
        reservation.setStatus(ReservationStatus.CANCELED);
        assertThatThrownBy(reservation::isPayable)
                .isInstanceOf(ApplicationException.class)
                .hasMessage(ErrorType.Concert.RESERVATION_ALREADY_CANCELED.getMessage());
    }

    @Test
    @DisplayName("pay 메서드 호출 시 예약 상태가 PAID로 변경되고 결제 ID가 설정됨")
    void pay_ChangesStatusToPaidAndSetsPaymentId() {
        Long paymentId = 100L;

        // pay 호출
        reservation.pay(paymentId);

        // 상태 및 결제 ID 검증
        assertThat(reservation.getStatus()).isEqualTo(ReservationStatus.PAID);
        assertThat(reservation.getPaymentId()).isEqualTo(paymentId);
    }
}
