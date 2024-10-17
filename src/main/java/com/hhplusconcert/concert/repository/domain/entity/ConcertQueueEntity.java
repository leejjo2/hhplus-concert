package com.hhplusconcert.concert.repository.domain.entity;

import com.hhplusconcert.concert.repository.domain.ConcertQueue;
import com.hhplusconcert.concert.repository.domain.vo.ConcertQueueStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CONCERT_QUEUE")
@Entity
public class ConcertQueueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long concertScheduleId;
    private String token;
    private ConcertQueueStatus status;
    private LocalDateTime enteredAt;
    private LocalDateTime expiredAt;

    public static ConcertQueue toDomain(ConcertQueueEntity entity) {
        if (entity == null) {
            return null;
        }
        return new ConcertQueue(
                entity.getId(),
                entity.getUserId(),
                entity.getConcertScheduleId(),
                entity.getToken(),
                entity.getStatus(),
                entity.getEnteredAt(),
                entity.getExpiredAt()
        );
    }

    public static ConcertQueueEntity fromDomain(ConcertQueue domain) {
        if (domain == null) {
            return null;
        }
        return new ConcertQueueEntity(
                domain.getId(),
                domain.getUserId(),
                domain.getConcertScheduleId(),
                domain.getToken(),
                domain.getStatus(),
                domain.getEnteredAt(),
                domain.getExpiredAt()
        );
    }
}
