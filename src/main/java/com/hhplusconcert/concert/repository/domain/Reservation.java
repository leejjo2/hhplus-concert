package com.hhplusconcert.concert.repository.domain;

import com.hhplusconcert.concert.repository.domain.vo.ReservationStatus;
import com.hhplusconcert.shared.error.ApplicationException;
import com.hhplusconcert.shared.error.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.EnumSet;

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

    private static final EnumSet<ReservationStatus> PAYABLE_STATUSES = EnumSet.of(
            ReservationStatus.TEMP_RESERVED,
            ReservationStatus.RESERVED
    );

    public boolean isPayable() {
        if (PAYABLE_STATUSES.contains(status)) {
            return true;
        }
        handleInvalidPayableState();  // 예외를 던지므로 반환값 필요 없음
        return false;
    }

    public void pay(Long paymentId) {
        if (!isPayable()) {
            throw new ApplicationException(ErrorType.INVALID_REQUEST);
        }
        this.paymentId = paymentId;
        this.status = ReservationStatus.PAID;
    }

    private void handleInvalidPayableState() {
        switch (status) {
            case PAID -> throw new ApplicationException(ErrorType.Concert.RESERVATION_ALREADY_PAID);
            case CANCELED -> throw new ApplicationException(ErrorType.Concert.RESERVATION_ALREADY_CANCELED);
            default -> throw new ApplicationException(ErrorType.INVALID_REQUEST);
        }
    }

    public void expire() {
        this.status = ReservationStatus.CANCELED;
    }
}
