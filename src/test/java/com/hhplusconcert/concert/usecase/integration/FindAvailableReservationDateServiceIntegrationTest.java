package com.hhplusconcert.concert.usecase.integration;

import com.hhplusconcert.concert.repository.ConcertScheduleRepository;
import com.hhplusconcert.concert.repository.domain.ConcertSchedule;
import com.hhplusconcert.concert.repository.domain.vo.ConcertScheduleStatus;
import com.hhplusconcert.concert.usecase.FindAvailableReservationDateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
public class FindAvailableReservationDateServiceIntegrationTest {

    @Autowired
    private FindAvailableReservationDateService findAvailableReservationDateService;

    @Autowired
    private ConcertScheduleRepository concertScheduleRepository;

    @BeforeEach
    public void setup() {
        // 테스트용 데이터 삽입
        ConcertSchedule schedule1 = new ConcertSchedule();
        schedule1.setId(1L);
        schedule1.setStatus(ConcertScheduleStatus.AVAILABLE);
        schedule1.setOpenDate(LocalDate.of(2024, 10, 1));

        ConcertSchedule schedule2 = new ConcertSchedule();
        schedule2.setId(1L);
        schedule2.setStatus(ConcertScheduleStatus.AVAILABLE);
        schedule2.setOpenDate(LocalDate.of(2024, 10, 2));

        ConcertSchedule schedule3 = new ConcertSchedule();
        schedule3.setId(2L);
        schedule3.setStatus(ConcertScheduleStatus.SOLD_OUT);  // 이 데이터는 조회되지 않음
        schedule3.setOpenDate(LocalDate.of(2024, 10, 3));

        concertScheduleRepository.save(schedule1);
        concertScheduleRepository.save(schedule2);
        concertScheduleRepository.save(schedule3);
    }

    @Test
    public void testFindAvailableReservationDates() {
        // given
        Long concertScheduleId = 1L;

        // when
        FindAvailableReservationDateService.Output output = findAvailableReservationDateService.execute(concertScheduleId);

        // then
        List<LocalDate> availableDates = output.getDate();
        assertThat(availableDates).hasSize(2);
        assertThat(availableDates).contains(LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 2));
    }
}
