package com.hhplusconcert.concert.repository.domain;

import com.hhplusconcert.concert.repository.domain.vo.ConcertScheduleStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConcertSchedule {
    private Long id;
    private Long concertId;
    private LocalDate openDate;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private int totalSeat;
    private int availableSeat;
    private ConcertScheduleStatus status;

}
