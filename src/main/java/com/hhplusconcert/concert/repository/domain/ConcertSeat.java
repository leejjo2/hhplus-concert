package com.hhplusconcert.concert.repository.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConcertSeat {
    private Long id;
    private Long concertScheduleId;
    private int amount;
    private int position;

}
