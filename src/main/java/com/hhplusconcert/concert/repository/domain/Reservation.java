package com.hhplusconcert.concert.repository.domain;

import com.hhplusconcert.concert.repository.domain.vo.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

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

    public boolean isPayable() {
        if (Set.of(ReservationStatus.TEMP_RESERVED, ReservationStatus.RESERVED).contains(status)) {
            return true;
        } else if (ReservationStatus.PAID.equals(status)) {
            throw new RuntimeException("이미 결제되었습니다.");
        } else if (ReservationStatus.CANCELED.equals(status)) {
            throw new RuntimeException("취소된 예약입니다.");
        } else {
            throw new RuntimeException();
        }
    }

    public void pay(Long paymentId) {
        this.paymentId = paymentId;
        this.status = ReservationStatus.PAID;
    }

}
