package com.hhplusconcert.concert.repository.domain.entity;

import com.hhplusconcert.concert.repository.domain.ConcertSchedule;
import com.hhplusconcert.concert.repository.domain.vo.ConcertScheduleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CONCERT_SCHEDULE")
@Entity
public class ConcertScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long concertId;
    private LocalDate openDate;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    @Enumerated(EnumType.STRING)
    private ConcertScheduleStatus status;

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
                domain.getStatus()
        );
    }
}
