package com.hhplusconcert.concert.usecase;

import com.hhplusconcert.concert.repository.ConcertQueueRepository;
import com.hhplusconcert.concert.repository.domain.ConcertQueue;
import com.hhplusconcert.concert.repository.domain.vo.ConcertQueueStatus;
import com.hhplusconcert.concert.token.JwtTokenProvider;
import com.hhplusconcert.concert.usecase.CreateQueueService.Output;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateQueueServiceTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private ConcertQueueRepository concertQueueRepository;

    @InjectMocks
    private CreateQueueService createQueueService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_shouldCreateConcertQueueAndReturnToken() {
        // given
        Long concertScheduleId = 1L;
        Long userId = 2L;
        String expectedToken = "generatedToken";


        // Mocking the behavior of jwtTokenProvider and repository
        when(jwtTokenProvider.generateToken(any())).thenReturn(expectedToken);

        // when
        Output result = createQueueService.execute(concertScheduleId, userId);

        // then
        // Verify that concertQueueRepository.create was called with the correct ConcertQueue
        ArgumentCaptor<ConcertQueue> concertQueueCaptor = ArgumentCaptor.forClass(ConcertQueue.class);
        verify(concertQueueRepository).create(concertQueueCaptor.capture());

        ConcertQueue capturedConcertQueue = concertQueueCaptor.getValue();
        assertThat(capturedConcertQueue.getConcertScheduleId()).isEqualTo(concertScheduleId);
        assertThat(capturedConcertQueue.getUserId()).isEqualTo(userId);
        assertThat(capturedConcertQueue.getStatus()).isEqualTo(ConcertQueueStatus.WAITING);
        assertThat(capturedConcertQueue.getEnteredAt()).isNotNull();

        // Verify the token generation
        verify(jwtTokenProvider).generateToken(any(String.class));
        assertThat(result.getToken()).isEqualTo(expectedToken);
    }
}
