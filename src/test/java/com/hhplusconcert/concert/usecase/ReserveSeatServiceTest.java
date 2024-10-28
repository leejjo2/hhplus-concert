package com.hhplusconcert.concert.usecase;

import com.hhplusconcert.concert.repository.ConcertSeatRepository;
import com.hhplusconcert.concert.repository.ReservationRepository;
import com.hhplusconcert.concert.repository.domain.ConcertSeat;
import com.hhplusconcert.concert.repository.domain.Reservation;
import com.hhplusconcert.concert.repository.domain.vo.ReservationStatus;
import com.hhplusconcert.shared.error.ApplicationException;
import com.hhplusconcert.shared.error.ErrorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@Rollback
public class ReserveSeatServiceTest {

    @Autowired
    private ReserveSeatService reserveSeatService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ConcertSeatRepository concertSeatRepository;

    private ConcertSeat testSeat;
    private Long userId = 1L;
    private Long concertScheduleId = 1L;

    @BeforeEach
    public void setup() {
        // 테스트 좌석 및 스케줄 생성
        testSeat = concertSeatRepository.save(new ConcertSeat(null, concertScheduleId, 100, 1, false));
    }

    @Test
    public void testExecute_Success() {
        // when
        ReserveSeatService.Output output = reserveSeatService.execute(concertScheduleId, testSeat.getId(), userId);

        // then
        assertThat(output.getReservationId()).isNotNull();

        Reservation reservation = reservationRepository.findById(output.getReservationId());
        assertThat(reservation.getSeatId()).isEqualTo(testSeat.getId());
        assertThat(reservation.getConcertScheduleId()).isEqualTo(concertScheduleId);
        assertThat(reservation.getStatus()).isEqualTo(ReservationStatus.RESERVED);
        assertThat(reservation.getReservedAt()).isBeforeOrEqualTo(LocalDateTime.now());

        ConcertSeat reservedSeat = concertSeatRepository.findById(testSeat.getId());
        assertThat(reservedSeat.getIsReserved()).isTrue();
    }

    @Test
    public void testExecute_SeatAlreadyReserved() {
        // given
        testSeat.setIsReserved(true);
        concertSeatRepository.save(testSeat);

        // when & then
        ApplicationException applicationException = assertThrows(ApplicationException.class, () ->
                reserveSeatService.execute(concertScheduleId, testSeat.getId(), userId));

        assertThat(applicationException.getMessage()).isEqualTo(ErrorType.Concert.CONCERT_SEAT_ALREADY_RESERVED.getMessage());
    }
}
