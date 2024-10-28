package com.hhplusconcert.concert.usecase;

import com.hhplusconcert.TestMockData;
import com.hhplusconcert.concert.repository.ConcertQueueRepository;
import com.hhplusconcert.concert.repository.domain.ConcertQueue;
import com.hhplusconcert.concert.repository.domain.vo.ConcertQueueStatus;
import com.hhplusconcert.concert.token.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CreateQueueServiceTest {

    TestMockData testMockData = new TestMockData();
    @Autowired
    private CreateQueueService createQueueService;
    @Autowired
    private ConcertQueueRepository concertQueueRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void shouldCreateQueueAndReturnToken() {
        // given
        Long concertScheduleId = TestMockData.Concert.ConcertSchedule.concertScheduleId_1; // 테스트에 사용할 콘서트 스케줄 ID
        Long userId = TestMockData.User.userId_123; // 사용자 ID

        // when
        CreateQueueService.Output output = createQueueService.execute(concertScheduleId, userId);

        // then
        // 반환된 토큰이 유효한지 검증 (예외 발생 여부 확인)
        String token = output.getToken();

        // try-catch로 예외 발생 여부 확인
        try {
            jwtTokenProvider.verifyToken(token);
        } catch (RuntimeException e) {
            // 검증에 실패한 경우 테스트 실패 처리
            throw new AssertionError("토큰 검증 중 예외가 발생했습니다.", e);
        }

        String tokenId = jwtTokenProvider.getClaimValue(token, JwtTokenProvider.QUEUE_TOKEN_ID);

        // 콘서트 큐 대기열이 생성되었는지 검증 (Optional 대신 객체로 직접 받음)
        ConcertQueue concertQueue = concertQueueRepository.findByToken(tokenId);
        assertThat(concertQueue).isNotNull(); // 대기열이 null이 아님을 검증

        assertThat(concertQueue.getUserId()).isEqualTo(userId);
        assertThat(concertQueue.getConcertScheduleId()).isEqualTo(concertScheduleId);
        assertThat(concertQueue.getStatus()).isEqualTo(ConcertQueueStatus.WAITING);
        assertThat(concertQueue.getEnteredAt()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(concertQueue.getToken()).isEqualTo(tokenId);
    }
}
