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

    public Output execute(Long concertScheduleId, Long seatId, Long userId) {
        Reservation reservation = new Reservation(null, userId, concertScheduleId, seatId, null, ReservationStatus.RESERVED, LocalDateTime.now());
        Reservation saved = reservationRepository.save(reservation);
        return new Output(saved.getId());
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Output {
        Long reservationId;
    }

}
