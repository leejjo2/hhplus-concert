package com.hhplusconcert.concert.usecase.integration;

import com.hhplusconcert.concert.aop.QueueTokenHolder;
import com.hhplusconcert.concert.repository.ConcertQueueRepository;
import com.hhplusconcert.concert.repository.domain.ConcertQueue;
import com.hhplusconcert.concert.repository.domain.vo.ConcertQueueStatus;
import com.hhplusconcert.concert.token.JwtTokenProvider;
import com.hhplusconcert.concert.usecase.FindQueueService;
import com.hhplusconcert.shared.error.ApplicationException;
import com.hhplusconcert.shared.error.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class FindQueueServiceIntegrationTest {

    @Autowired
    private FindQueueService findQueueService;

    @MockBean
    private ConcertQueueRepository concertQueueRepository;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("대기열에서 유저가 대기 중일 때, 올바른 대기 번호가 반환되어야 한다")
    void testExecute_whenUserIsWaiting() {
        // given
        Long concertScheduleId = 1L;
        String queueTokenId = "test-queue-token-id";
        String token = "test-token";

        ConcertQueue mockConcertQueue = new ConcertQueue();
        mockConcertQueue.setStatus(ConcertQueueStatus.WAITING);
        mockConcertQueue.setEnteredAt(LocalDateTime.now());

        when(QueueTokenHolder.getToken()).thenReturn(token);
        when(jwtTokenProvider.getClaimValue(anyString(), eq(JwtTokenProvider.QUEUE_TOKEN_ID)))
                .thenReturn(queueTokenId);
        when(concertQueueRepository.findByToken(queueTokenId)).thenReturn(mockConcertQueue);
        when(concertQueueRepository.countByConcertScheduleIdAndStatusAndEnteredAtBefore(
                anyLong(), eq(ConcertQueueStatus.WAITING), any()))
                .thenReturn(10L);  // 10명의 유저가 대기 중이라고 가정

        // when
        FindQueueService.Output output = findQueueService.execute(concertScheduleId);

        // then
        assertNotNull(output);
        assertEquals(10L, output.getWaitingNumber());
    }

    @Test
    @DisplayName("대기열에서 유저가 처리 중일 때, 대기 번호는 0이 반환되어야 한다")
    void testExecute_whenUserIsInProgress() {
        // given
        Long concertScheduleId = 1L;
        String queueTokenId = "test-queue-token-id";
        String token = "test-token";

        ConcertQueue mockConcertQueue = new ConcertQueue();
        mockConcertQueue.setStatus(ConcertQueueStatus.PROGRESS);

        when(QueueTokenHolder.getToken()).thenReturn(token);
        when(jwtTokenProvider.getClaimValue(anyString(), eq(JwtTokenProvider.QUEUE_TOKEN_ID)))
                .thenReturn(queueTokenId);
        when(concertQueueRepository.findByToken(queueTokenId)).thenReturn(mockConcertQueue);

        // when
        FindQueueService.Output output = findQueueService.execute(concertScheduleId);

        // then
        assertNotNull(output);
        assertEquals(0L, output.getWaitingNumber());
    }

    @Test
    @DisplayName("대기열 상태가 유효하지 않을 때, 예외가 발생해야 한다")
    void testExecute_whenInvalidQueueStatus() {
        // given
        Long concertScheduleId = 1L;
        String queueTokenId = "test-queue-token-id";
        String token = "test-token";

        ConcertQueue mockConcertQueue = new ConcertQueue();
        mockConcertQueue.setStatus(ConcertQueueStatus.EXPIRED);  // 잘못된 상태 설정

        when(QueueTokenHolder.getToken()).thenReturn(token);
        when(jwtTokenProvider.getClaimValue(anyString(), eq(JwtTokenProvider.QUEUE_TOKEN_ID)))
                .thenReturn(queueTokenId);
        when(concertQueueRepository.findByToken(queueTokenId)).thenReturn(mockConcertQueue);

        // when & then
        ApplicationException exception = assertThrows(ApplicationException.class, () ->
                findQueueService.execute(concertScheduleId)
        );

        assertEquals(ErrorType.WaitingQueue.INVALID_STATUS, exception.getErrorType());
    }
}
