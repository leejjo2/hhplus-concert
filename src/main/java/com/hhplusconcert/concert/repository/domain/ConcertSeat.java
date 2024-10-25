package com.hhplusconcert.concert.repository.domain;

import com.hhplusconcert.shared.error.ApplicationException;
import com.hhplusconcert.shared.error.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConcertSeat {
    private Long id;
    private Long concertScheduleId;
    private int amount;
    private int position;
    private Boolean isReserved;

    private Long version;


    public void reserve() {
        if (this.isReserved) {
            throw new ApplicationException(ErrorType.Concert.CONCERT_SEAT_ALREADY_RESERVED);
        }

        this.isReserved = true;
    }
}
