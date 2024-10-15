package com.hhplusconcert.concert.repository.domain;

import com.hhplusconcert.concert.repository.domain.vo.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    private Long id;
    private Long userId;
    private Long concertScheduleId;
    private Long seatId;
    private Long paymentId;
    private ReservationStatus status;
    private LocalDateTime reservedAt;

}
