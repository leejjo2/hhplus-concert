package com.hhplusconcert.concert.usecase;

import com.hhplusconcert.concert.repository.PaymentRepository;
import com.hhplusconcert.concert.repository.ReservationRepository;
import com.hhplusconcert.concert.repository.domain.Payment;
import com.hhplusconcert.concert.repository.domain.Reservation;
import com.hhplusconcert.concert.repository.domain.vo.PaymentStatus;
import com.hhplusconcert.user.repository.UserRepository;
import com.hhplusconcert.user.repository.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PurchaseSeatService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public Output execute(Long reservationId, Long useId, Input input) {
        User user = userRepository.findById(useId);
        Reservation reservation = reservationRepository.findById(reservationId);

        if (reservation.isPayable()) {
            Payment payment = new Payment(null, useId, input.getPurchaseAmount(), PaymentStatus.PROGRESS, LocalDateTime.now());
            Payment savedPayment = paymentRepository.save(payment);

            reservation.pay(savedPayment.getId());
            reservationRepository.save(reservation);

            user.deductAmount(input.getPurchaseAmount());
            userRepository.save(user);

            return new Output(savedPayment.getId());
        } else {
            throw new RuntimeException("에러 발생.");
        }

    }

    @Getter
    @AllArgsConstructor
    public static class Input {
        Integer purchaseAmount;
    }

    @AllArgsConstructor
    @Getter
    public static class Output {
        Long purchaseNumber;
    }


}
