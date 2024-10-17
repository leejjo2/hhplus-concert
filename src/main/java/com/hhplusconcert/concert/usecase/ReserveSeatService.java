package com.hhplusconcert.concert.usecase;

import com.hhplusconcert.concert.repository.ReservationRepository;
import com.hhplusconcert.concert.repository.domain.Reservation;
import com.hhplusconcert.concert.repository.domain.vo.ReservationStatus;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReserveSeatService {
    private final ReservationRepository reservationRepository;

    public Output execute(Long concertScheduleId, Long seatId, Input input) {
        Reservation reservation = new Reservation(null, input.getUserId(), concertScheduleId, seatId, null, ReservationStatus.RESERVED, LocalDateTime.now());
        Reservation saved = reservationRepository.save(reservation);
        return new Output(saved.getId());
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Input {
        Long userId;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Output {
        Long reservationId;
    }

}
