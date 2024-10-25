package com.hhplusconcert.concert.usecase;

import com.hhplusconcert.concert.repository.ConcertSeatRepository;
import com.hhplusconcert.concert.repository.ReservationRepository;
import com.hhplusconcert.concert.repository.domain.ConcertSeat;
import com.hhplusconcert.concert.repository.domain.Reservation;
import com.hhplusconcert.concert.repository.domain.vo.ReservationStatus;
import com.hhplusconcert.shared.error.ApplicationException;
import lombok.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReserveSeatService {
    private final ReservationRepository reservationRepository;
    private final ConcertSeatRepository concertSeatRepository;

    @Retryable(
            retryFor = RuntimeException.class,
            noRetryFor = ApplicationException.class,
            backoff = @Backoff(50)
    )
    @Transactional
    public Output execute(Long concertScheduleId, Long seatId, Long userId) {
        ConcertSeat concertSeat = concertSeatRepository.findByIdWithLock(seatId);
        concertSeat.reserve();

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
