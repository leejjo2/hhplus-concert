package com.hhplusconcert.concert.usecase;

import com.hhplusconcert.concert.repository.ConcertQueueRepository;
import com.hhplusconcert.concert.repository.domain.ConcertQueue;
import com.hhplusconcert.concert.repository.domain.vo.ConcertQueueStatus;
import com.hhplusconcert.concert.token.JwtTokenProvider;
import com.hhplusconcert.scheduler.ConcertScheduler;
import com.hhplusconcert.shared.error.ApplicationException;
import com.hhplusconcert.shared.error.ErrorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@Rollback
class FindQueueServiceTest {

    @Autowired
    private FindQueueService findQueueService;

    @Autowired
    private ConcertQueueRepository concertQueueRepository;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    // scheduler 비활성화
    @MockBean
    private ConcertScheduler concertScheduler;

    private Long concertScheduleId;
    private String queueTokenId;
    private String mockQueueToken;

    @BeforeEach
    void setUp() {
        concertScheduleId = 1L;
        queueTokenId = "testQueueTokenId";
        mockQueueToken = "mockedToken";

        when(jwtTokenProvider.getClaimValue(mockQueueToken, JwtTokenProvider.QUEUE_TOKEN_ID))
                .thenReturn(queueTokenId);

        // 초기 대기열 엔티티 저장
        ConcertQueue concertQueue = new ConcertQueue();
        concertQueue.setToken(queueTokenId);
        concertQueue.setStatus(ConcertQueueStatus.WAITING);
        concertQueue.setEnteredAt(LocalDateTime.now().minusMinutes(10));
        concertQueue.setConcertScheduleId(concertScheduleId);

        concertQueueRepository.create(concertQueue);
    }

    @Test
    @DisplayName("대기열 상태가 WAITING일 때 대기 인원이 올바르게 반환되어야 한다")
    void testExecuteWithWaitingStatus() {

        String queueTokenId_2 = "testQueueTokenId_2";
        String mockQueueToken_2 = "mockedToken_2";

        when(jwtTokenProvider.getClaimValue(mockQueueToken_2, JwtTokenProvider.QUEUE_TOKEN_ID))
                .thenReturn(queueTokenId_2);
        // 초기 대기열 엔티티 저장
        ConcertQueue concertQueue = new ConcertQueue();
        concertQueue.setToken(queueTokenId_2);
        concertQueue.setStatus(ConcertQueueStatus.WAITING);
        concertQueue.setEnteredAt(LocalDateTime.now());
        concertQueue.setConcertScheduleId(concertScheduleId);

        concertQueueRepository.create(concertQueue);

        FindQueueService.Output output = findQueueService.execute(mockQueueToken_2, concertScheduleId);

        assertEquals(1L, output.getWaitingNumber());
    }

    @Test
    @DisplayName("대기열 상태가 PROGRESS일 때 대기 인원은 0으로 반환되어야 한다")
    void testExecuteWithProgressStatus() {
        ConcertQueue concertQueue = concertQueueRepository.findByToken(queueTokenId);
        concertQueue.setStatus(ConcertQueueStatus.PROGRESS);
        concertQueueRepository.create(concertQueue);

        FindQueueService.Output output = findQueueService.execute(mockQueueToken, concertScheduleId);

        assertEquals(0L, output.getWaitingNumber());
    }

    @Test
    @DisplayName("대기열 상태가 잘못된 경우 예외가 발생해야 한다")
    void testExecuteWithInvalidStatus() {
        ConcertQueue concertQueue = concertQueueRepository.findByToken(queueTokenId);
        concertQueue.setStatus(ConcertQueueStatus.EXPIRED);
        concertQueueRepository.create(concertQueue);

        ApplicationException exception = assertThrows(ApplicationException.class,
                () -> findQueueService.execute(mockQueueToken, concertScheduleId));

        assertEquals(ErrorType.WaitingQueue.INVALID_STATUS, exception.getErrorType());
    }
}

