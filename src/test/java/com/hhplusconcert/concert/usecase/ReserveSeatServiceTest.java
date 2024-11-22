package com.hhplusconcert.concert.usecase;

import com.hhplusconcert.concert.repository.ConcertSeatRepository;
import com.hhplusconcert.concert.repository.ReservationRepository;
import com.hhplusconcert.concert.repository.domain.ConcertSeat;
import com.hhplusconcert.concert.repository.domain.Reservation;
import com.hhplusconcert.concert.repository.domain.vo.ReservationStatus;
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
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ReserveSeatServiceTest {

    private static final Logger logger = Logger.getLogger(
            ReserveSeatServiceTest.class.getName());

    @Autowired
    private ReserveSeatService reserveSeatService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ConcertSeatRepository concertSeatRepository;
    private final Long userId = 1L;

    private ConcertSeat testSeat;
    private final Long concertScheduleId = 1L;
    @MockBean
    private ConcertScheduler concertScheduler;

    @BeforeEach
    public void setup() {
        testSeat = concertSeatRepository.save(new ConcertSeat(null, concertScheduleId, 100, 1, false));
    }

    @Test
    @DisplayName("여러 사용자가 동일한 좌석을 동시에 예약하려 할 때, 오직 하나의 예약만 성공해야 합니다.")
    void testConcurrentReservationWithCompletableFuture() throws InterruptedException, ExecutionException {
        int numberOfUsers = 100;
        List<CompletableFuture<Boolean>> futures = IntStream.range(0, numberOfUsers)
                .mapToObj(i -> CompletableFuture.supplyAsync(() -> {
                    try {
                        ReserveSeatService.Output output = reserveSeatService.execute(concertScheduleId, testSeat.getId(), (long) i);
                        return output.getReservationId() != null;
                    } catch (Exception e) {
                        return false;
                    }
                }))
                .toList();

        long start = System.currentTimeMillis();
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        long end = System.currentTimeMillis();

        logger.info("Execution Time with Pessimistic Lock: " + (end - start) + "ms");

        long successfulReservations = futures.stream()
                .filter(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        return false;
                    }
                })
                .count();

        assertEquals(1, successfulReservations, "동시에 요청할 경우, 하나의 예약만 성공해야 합니다.");
    }

    @Test
    @Transactional
    @Rollback
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
    @Transactional
    @Rollback
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
