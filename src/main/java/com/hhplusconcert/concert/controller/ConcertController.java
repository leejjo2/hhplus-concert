package com.hhplusconcert.concert.controller;

import com.hhplusconcert.concert.aop.ConcertTokenRequired;
import com.hhplusconcert.concert.usecase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/concerts")
public class ConcertController implements ConcertControllerInterface {

    private final CreateQueueService createQueueService;
    private final FindQueueService findQueueService;
    private final FindAvailableReservationDateService findAvailableReservationDateService;
    private final FindReservationSeatService findReservationSeatService;
    private final ReserveSeatService reserveSeatService;
    private final PurchaseSeatService purchaseSeatService;

    @PostMapping("/{concertScheduleId}/queue-token")
    @Override
    public ResponseEntity<CreateQueueService.Output> createQueue(
            @PathVariable("concertScheduleId") Long concertScheduleId,
            @RequestBody CreateQueueService.Input input
    ) {
        CreateQueueService.Output output = createQueueService.execute(concertScheduleId, input);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @Override
    @ConcertTokenRequired
    @GetMapping("/{concertScheduleId}/queue")
    public ResponseEntity<FindQueueService.Output> findQueue(
            @PathVariable("concertScheduleId") Long concertScheduleId
    ) {
        FindQueueService.Output output = findQueueService.execute(concertScheduleId);
        return ResponseEntity.ok(output);
    }

    @Override
    @ConcertTokenRequired
    @GetMapping("/{concertScheduleId}/reservation/available-date")
    public ResponseEntity<FindAvailableReservationDateService.Output> findReservationDate(
            @PathVariable("concertScheduleId") Long concertScheduleId
    ) {
        FindAvailableReservationDateService.Output output = findAvailableReservationDateService.execute(concertScheduleId);
        return ResponseEntity.ok(output);
    }

    @Override
    @ConcertTokenRequired
    @GetMapping("/{concertScheduleId}/reservation/seat")
    public ResponseEntity<List<FindReservationSeatService.Output>> findReservationSeat(
            @PathVariable("concertScheduleId") Long concertScheduleId
    ) {
        List<FindReservationSeatService.Output> output = findReservationSeatService.execute(concertScheduleId);
        return ResponseEntity.ok(output);
    }

    @Override
    @ConcertTokenRequired
    @PostMapping("/{concertScheduleId}/reservation/seat/{seatId}")
    public ResponseEntity<ReserveSeatService.Output> reserveSeat(
            @PathVariable("concertScheduleId") Long concertScheduleId,
            @PathVariable("seatId") Long seatId,
            @RequestBody ReserveSeatService.Input input
    ) {

        ReserveSeatService.Output output = reserveSeatService.execute(concertScheduleId, seatId, input);
        return ResponseEntity.ok(output).status(HttpStatus.CREATED).build();
    }

    @Override
    @ConcertTokenRequired
    @PostMapping("/{reservationId}/purchase")
    public ResponseEntity<PurchaseSeatService.Output> purchaseSeat(
            @PathVariable("reservationId") Long reservationId,
            @RequestBody PurchaseSeatService.Input input
    ) {

        PurchaseSeatService.Output output = purchaseSeatService.execute(reservationId, input);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }
}
