package com.hhplusconcert.concert.usecase;

import com.hhplusconcert.TestMockData;
import com.hhplusconcert.concert.repository.ConcertScheduleRepository;
import com.hhplusconcert.concert.repository.domain.ConcertSchedule;
import com.hhplusconcert.concert.repository.domain.vo.ConcertScheduleStatus;
import com.hhplusconcert.concert.repository.orm.ConcertScheduleJpaRepository;
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
public class FindAvailableReservationDateServiceTest {

    @Autowired
    private FindAvailableReservationDateService findAvailableReservationDateService;

    @Autowired
    private ConcertScheduleRepository concertScheduleRepository;

    @Autowired
    private ConcertScheduleJpaRepository concertScheduleJpaRepository;

    @BeforeEach
    public void setup() {
        concertScheduleJpaRepository.deleteAll();

        // 테스트용 데이터 삽입
        ConcertSchedule schedule1 = new ConcertSchedule();
        schedule1.setId(TestMockData.Concert.ConcertSchedule.concertScheduleId_1);
        schedule1.setConcertId(TestMockData.Concert.concertId_1);
        schedule1.setStatus(ConcertScheduleStatus.AVAILABLE);
        schedule1.setOpenDate(TestMockData.Concert.ConcertSchedule.openDate_241001);

        ConcertSchedule schedule2 = new ConcertSchedule();
        schedule2.setId(TestMockData.Concert.ConcertSchedule.concertScheduleId_2);
        schedule2.setConcertId(TestMockData.Concert.concertId_1);
        schedule2.setStatus(ConcertScheduleStatus.AVAILABLE);
        schedule2.setOpenDate(TestMockData.Concert.ConcertSchedule.openDate_241002);

        ConcertSchedule schedule3 = new ConcertSchedule();
        schedule3.setId(TestMockData.Concert.ConcertSchedule.concertScheduleId_3);
        schedule3.setConcertId(TestMockData.Concert.concertId_1);
        schedule3.setStatus(ConcertScheduleStatus.SOLD_OUT);  // 이 데이터는 조회되지 않음
        schedule3.setOpenDate(TestMockData.Concert.ConcertSchedule.openDate_241003);

        concertScheduleRepository.save(schedule1);
        concertScheduleRepository.save(schedule2);
        concertScheduleRepository.save(schedule3);
    }

    @Test
    public void testFindAvailableReservationDates() {
        // given
        Long concertId = TestMockData.Concert.concertId_1;

        // when
        FindAvailableReservationDateService.Output output = findAvailableReservationDateService.execute(concertId);

        // then
        List<LocalDate> availableDates = output.getDate();
        assertThat(availableDates).hasSize(2);
        assertThat(availableDates).contains(TestMockData.Concert.ConcertSchedule.openDate_241001, TestMockData.Concert.ConcertSchedule.openDate_241002);
    }
}
