package com.hhplusconcert.concert.controller;

import com.hhplusconcert.concert.aop.ConcertTokenRequired;
import com.hhplusconcert.concert.usecase.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/concerts")
public class ConcertController implements ConcertControllerInterface {

    @PostMapping("/{concertScheduleId}/queue-token")
    @Override
    public ResponseEntity<CreateQueueUseCase.Output> createQueue(
            @PathVariable("concertScheduleId") Long concertScheduleId,
            @RequestBody CreateQueueUseCase.Input input
    ) {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl9pZCI6InRva2VuX2lkXzEiLCJ1c2VySWQiOiJleGFtcGxlVXNlciIsImV4cCI6MTcyODYxMjY5Niwid2F0aW5nX251bWJlciI6MTIzNH0.8PooXzqNvBdkgXt6GnMaBGOdSyMvl8ziPdl5M4QSvR4";
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateQueueUseCase.Output(token));
    }

    @Override
    @ConcertTokenRequired
    @GetMapping("/{concertScheduleId}/queue")
    public ResponseEntity<FindQueueUseCase.Output> findQueue(
            @PathVariable("concertScheduleId") Long concertScheduleId
    ) {
        int waitingNumber = 10; // 실제 대기 번호 조회 로직 필요
        return ResponseEntity.ok(new FindQueueUseCase.Output(waitingNumber));
    }

    @Override
    @ConcertTokenRequired
    @GetMapping("/{concertScheduleId}/reservation/date")
    public ResponseEntity<FindReservationDateUseCase.Output> findReservationDate(
            @PathVariable("concertScheduleId") Long concertScheduleId,
            @RequestParam(value = "status") String status
    ) {
        List<LocalDate> availableDates = List.of(
                LocalDate.parse("2024-10-09"),
                LocalDate.parse("2024-10-10")
        );
        return ResponseEntity.ok(new FindReservationDateUseCase.Output(availableDates));
    }

    @Override
    @ConcertTokenRequired
    @GetMapping("/{concertScheduleId}/reservation/seat")
    public ResponseEntity<List<FindReservationSeatUseCase.Output>> findReservationSeat(
            @PathVariable("concertScheduleId") Long concertScheduleId,
            @RequestParam(value = "concertOpenDate") LocalDate concertOpenDate
    ) {
        List<FindReservationSeatUseCase.Output> seats = List.of(
                new FindReservationSeatUseCase.Output(1L, 1, 10000, "AVAILABLE"),
                new FindReservationSeatUseCase.Output(2L, 2, 10000, "TEMP_RESERVED"),
                new FindReservationSeatUseCase.Output(3L, 3, 10000, "RESERVED")
        );
        return ResponseEntity.ok(seats);
    }

    @Override
    @ConcertTokenRequired
    @PostMapping("/{concertScheduleId}/reservation/seat/{seatId}")
    public ResponseEntity<Void> reserveSeat(
            @PathVariable("concertScheduleId") Long concertScheduleId,
            @PathVariable("seatId") Long seatId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @ConcertTokenRequired
    @PostMapping("/{concertScheduleId}/purchase/seat/{seatId}")
    public ResponseEntity<PurchaseSeatUseCase.Output> purchaseSeat(
            @PathVariable("concertScheduleId") Long concertScheduleId,
            @PathVariable("seatId") Long seatId,
            @RequestBody PurchaseSeatUseCase.Input input
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new PurchaseSeatUseCase.Output(
                1L,
                "concert name",
                LocalDate.of(2024, 10, 19), // 콘서트 날짜
                333, // 구매한 좌석 번호
                150000, // 좌석 가격
                LocalDateTime.of(2024, 10, 1, 0, 0, 0) // 구매 시간
        ));
    }
}
