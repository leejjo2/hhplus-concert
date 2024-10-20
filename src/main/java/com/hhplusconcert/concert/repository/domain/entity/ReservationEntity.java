package com.hhplusconcert.concert.repository.domain.entity;

import com.hhplusconcert.concert.repository.domain.Reservation;
import com.hhplusconcert.concert.repository.domain.vo.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "RESERVATION")
@Entity
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long concertScheduleId;
    private Long seatId;
    private Long paymentId;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    private LocalDateTime reservedAt;

    public static Reservation toDomain(ReservationEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Reservation(
                entity.getId(),
                entity.getUserId(),
                entity.getConcertScheduleId(),
                entity.getSeatId(),
                entity.getPaymentId(),
                entity.getStatus(),
                entity.getReservedAt()
        );
    }

    public static ReservationEntity fromDomain(Reservation domain) {
        if (domain == null) {
            return null;
        }
        return new ReservationEntity(
                domain.getId(),
                domain.getUserId(),
                domain.getConcertScheduleId(),
                domain.getSeatId(),
                domain.getPaymentId(),
                domain.getStatus(),
                domain.getReservedAt()
        );
    }

}
