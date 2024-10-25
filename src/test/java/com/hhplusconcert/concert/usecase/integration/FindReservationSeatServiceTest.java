package com.hhplusconcert.concert.usecase.integration;

import com.hhplusconcert.concert.repository.ConcertSeatRepository;
import com.hhplusconcert.concert.repository.domain.ConcertSeat;
import com.hhplusconcert.concert.usecase.FindReservationSeatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
public class FindReservationSeatServiceTest {

    @Autowired
    private FindReservationSeatService findReservationSeatService;

    @Autowired
    private ConcertSeatRepository concertSeatRepository;

    @BeforeEach
    public void setup() {
        // 테스트용 콘서트 좌석 데이터 생성
        ConcertSeat seat1 = new ConcertSeat();
        seat1.setConcertScheduleId(1L);
        seat1.setPosition(1);
        seat1.setAmount(100);
        seat1.setIsReserved(false);

        ConcertSeat seat2 = new ConcertSeat();
        seat2.setConcertScheduleId(1L);
        seat2.setPosition(2);
        seat2.setAmount(120);
        seat2.setIsReserved(true);

        ConcertSeat seat3 = new ConcertSeat();
        seat3.setConcertScheduleId(2L);  // 다른 콘서트 스케줄 아이디
        seat3.setPosition(3);
        seat3.setAmount(150);
        seat3.setIsReserved(false);

        concertSeatRepository.save(seat1);
        concertSeatRepository.save(seat2);
        concertSeatRepository.save(seat3);
    }

    @Test
    public void testFindReservationSeats() {
        // given
        Long concertScheduleId = 1L;

        // when
        List<FindReservationSeatService.Output> result = findReservationSeatService.execute(concertScheduleId);

        // then
        assertThat(result).hasSize(2);  // concertScheduleId가 1L인 좌석 2개만 나와야 함
        assertThat(result.get(0).getSeatId()).isNotNull();
        assertThat(result.get(0).getPosition()).isEqualTo(1);
        assertThat(result.get(0).getPrice()).isEqualTo(100);
        assertThat(result.get(0).isReserved()).isFalse();

        assertThat(result.get(1).getPosition()).isEqualTo(2);
        assertThat(result.get(1).getPrice()).isEqualTo(120);
        assertThat(result.get(1).isReserved()).isTrue();
    }
}
