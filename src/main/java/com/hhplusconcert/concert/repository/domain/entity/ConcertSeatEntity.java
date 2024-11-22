package com.hhplusconcert.concert.repository.domain.entity;

import com.hhplusconcert.concert.repository.domain.ConcertSeat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CONCERT_SEAT")
@Entity
public class ConcertSeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long concertScheduleId;
    private int amount;
    private int position;
    @Column(nullable = false)
    private Boolean isReserved;


    public static ConcertSeat toDomain(ConcertSeatEntity entity) {
        if (entity == null) {
            return null;
        }
        return new ConcertSeat(
                entity.getId(),
                entity.getConcertScheduleId(),
                entity.getAmount(),
                entity.getPosition(),
                entity.getIsReserved()
        );
    }

    public static ConcertSeatEntity fromDomain(ConcertSeat domain) {
        if (domain == null) {
            return null;
        }
        return new ConcertSeatEntity(
                domain.getId(),
                domain.getConcertScheduleId(),
                domain.getAmount(),
                domain.getPosition(),
                domain.getIsReserved()
        );
    }
}
