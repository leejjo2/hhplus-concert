package com.hhplusconcert.concert.repository.domain;

import com.hhplusconcert.concert.repository.domain.vo.ConcertQueueStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConcertQueue {
    private Long id;
    private Long userId;
    private Long concertScheduleId;
    private String token;
    private ConcertQueueStatus status;
    private LocalDateTime enteredAt;
    private LocalDateTime expiredAt;
}
