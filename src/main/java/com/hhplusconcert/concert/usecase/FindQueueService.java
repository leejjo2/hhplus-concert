package com.hhplusconcert.concert.usecase;

import com.hhplusconcert.concert.aop.QueueTokenHolder;
import com.hhplusconcert.concert.repository.ConcertQueueRepository;
import com.hhplusconcert.concert.repository.domain.ConcertQueue;
import com.hhplusconcert.concert.repository.domain.vo.ConcertQueueStatus;
import com.hhplusconcert.concert.token.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindQueueService {

    private final ConcertQueueRepository concertQueueRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public Output execute(Long concertScheduleId) {

        String token = QueueTokenHolder.getToken();
        String queueTokenId = jwtTokenProvider.getClaimValue(token, JwtTokenProvider.QUEUE_TOKEN_ID);

        log.info("tokenId : {}", queueTokenId);
        ConcertQueue concertQueue = concertQueueRepository.findByToken(queueTokenId);
        switch (concertQueue.getStatus()) {
            case WAITING -> {
                Long before = concertQueueRepository.countByConcertScheduleIdAndStatusAndEnteredAtBefore(concertScheduleId, ConcertQueueStatus.WAITING, concertQueue.getEnteredAt());
                return new Output(before);
            }
            case PROGRESS -> {
                return new Output(0L);
            }
            default -> {
                throw new RuntimeException("유효하지 않은 대기열입니다.");
            }
        }
    }

    @AllArgsConstructor
    @Getter
    public static class Output {
        Long waitingNumber;
    }

}
