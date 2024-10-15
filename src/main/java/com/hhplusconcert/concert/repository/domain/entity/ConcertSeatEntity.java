package com.hhplusconcert.concert.repository.domain.entity;

import com.hhplusconcert.concert.repository.domain.ConcertSchedule;
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

    public static ConcertSchedule toDomain(ConcertScheduleEntity entity) {
        if (entity == null) {
            return null;
        }
        return new ConcertSchedule(
                entity.getId(),
                entity.getConcertId(),
                entity.getOpenDate(),
                entity.getStartAt(),
                entity.getEndAt(),
                entity.getTotalSeat(),
                entity.getAvailableSeat(),
                entity.getStatus()
        );
    }

    public static ConcertScheduleEntity fromDomain(ConcertSchedule domain) {
        if (domain == null) {
            return null;
        }
        return new ConcertScheduleEntity(
                domain.getId(),
                domain.getConcertId(),
                domain.getOpenDate(),
                domain.getStartAt(),
                domain.getEndAt(),
                domain.getTotalSeat(),
                domain.getAvailableSeat(),
                domain.getStatus()
        );
    }
}
