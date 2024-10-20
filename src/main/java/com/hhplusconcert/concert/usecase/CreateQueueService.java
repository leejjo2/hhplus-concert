package com.hhplusconcert.concert.usecase;

import com.hhplusconcert.concert.repository.ConcertQueueRepository;
import com.hhplusconcert.concert.repository.domain.ConcertQueue;
import com.hhplusconcert.concert.repository.domain.vo.ConcertQueueStatus;
import com.hhplusconcert.concert.token.JwtTokenProvider;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateQueueService {

    private final JwtTokenProvider jwtTokenProvider;
    private final ConcertQueueRepository concertQueueRepository;

    public Output execute(Long concertScheduleId, Input input) {

        String tokenId = UUID.randomUUID().toString();
        ConcertQueue concertQueue = new ConcertQueue(
                null,
                input.userId,
                concertScheduleId,
                tokenId,
                ConcertQueueStatus.WAITING,
                LocalDateTime.now(),
                null
        );
        concertQueueRepository.create(concertQueue);
        return new Output(jwtTokenProvider.generateToken(tokenId));
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Input {
        Long userId;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Output {
        String token;
    }
}
