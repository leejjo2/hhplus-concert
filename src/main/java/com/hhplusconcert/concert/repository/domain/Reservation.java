package com.hhplusconcert.concert.repository.domain;

import com.hhplusconcert.concert.repository.domain.vo.ReservationStatus;
import com.hhplusconcert.shared.error.ApplicationException;
import com.hhplusconcert.shared.error.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
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

    public boolean isPayable() {
        if (Set.of(ReservationStatus.TEMP_RESERVED, ReservationStatus.RESERVED).contains(status)) {
            return true;
        } else if (ReservationStatus.PAID.equals(status)) {
            throw new ApplicationException(ErrorType.Concert.RESERVATION_ALREADY_PAID);
        } else if (ReservationStatus.CANCELED.equals(status)) {
            throw new ApplicationException(ErrorType.Concert.RESERVATION_ALREADY_CANCELED);
        } else {
            throw new ApplicationException(ErrorType.INVALID_REQUEST);
        }
    }

    public void pay(Long paymentId) {
        this.paymentId = paymentId;
        this.status = ReservationStatus.PAID;
    }

}
