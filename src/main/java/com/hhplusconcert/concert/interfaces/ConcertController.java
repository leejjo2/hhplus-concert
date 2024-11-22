package com.hhplusconcert.concert.interfaces;

import com.hhplusconcert.concert.aop.ConcertTokenRequired;
import com.hhplusconcert.concert.interfaces.dto.request.PurchaseSeatRequest;
import com.hhplusconcert.concert.interfaces.dto.response.*;
import com.hhplusconcert.concert.usecase.*;
import com.hhplusconcert.shared.header.SharedHttpHeader;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/concerts")
public class ConcertController implements IConcertController {
    private final CreateQueueService createQueueService;
    private final FindQueueService findQueueService;
    private final FindAvailableReservationDateService findAvailableReservationDateService;
    private final FindReservationSeatService findReservationSeatService;
    private final ReserveSeatService reserveSeatService;
    private final PurchaseSeatService purchaseSeatService;

    @PostMapping("/{concertScheduleId}/queue-token")
    @Override
    public ResponseEntity<CreateQueueResponse> createQueue(
            @RequestHeader(SharedHttpHeader.X_USER_ID) Long userId,
            @PathVariable("concertScheduleId") Long concertScheduleId
    ) {
        CreateQueueService.Output output = createQueueService.execute(concertScheduleId, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(CreateQueueResponse.fromOutput(output));
    }

    @Override
    @ConcertTokenRequired
    @GetMapping("/{concertScheduleId}/queue")
    public ResponseEntity<FindQueueResponse> findQueue(
            @RequestHeader(SharedHttpHeader.X_QUEUE_TOKEN) String queueToken,
            @PathVariable("concertScheduleId") Long concertScheduleId
    ) {
        FindQueueService.Output output = findQueueService.execute(queueToken, concertScheduleId);
        return ResponseEntity.ok(FindQueueResponse.fromOutput(output));
    }

    @Override
    @ConcertTokenRequired
    @GetMapping("/{concertId}/reservation/available-date")
    public ResponseEntity<FindAvailableReservationDateResponse> findReservationDate(
            @RequestHeader(SharedHttpHeader.X_QUEUE_TOKEN) String queueToken,
            @PathVariable("concertId") Long concertId
    ) {
        FindAvailableReservationDateService.Output output = findAvailableReservationDateService.execute(concertId);
        return ResponseEntity.ok(FindAvailableReservationDateResponse.fromOutput(output));
    }

    @Override
    @ConcertTokenRequired
    @GetMapping("/{concertScheduleId}/reservation/seat")
    public ResponseEntity<List<FindReservationSeatResponse>> findReservationSeat(
            @RequestHeader(SharedHttpHeader.X_QUEUE_TOKEN) String queueToken,
            @PathVariable("concertScheduleId") Long concertScheduleId
    ) {
        List<FindReservationSeatService.Output> output = findReservationSeatService.execute(concertScheduleId);
        return ResponseEntity.ok(FindReservationSeatResponse.fromOutput(output));
    }

    @Override
    @ConcertTokenRequired
    @PostMapping("/{concertScheduleId}/reservation/seat/{seatId}")
    public ResponseEntity<ReserveSeatResponse> reserveSeat(
            @RequestHeader(SharedHttpHeader.X_USER_ID) Long userId,
            @RequestHeader(SharedHttpHeader.X_QUEUE_TOKEN) String queueToken,
            @PathVariable("concertScheduleId") Long concertScheduleId,
            @PathVariable("seatId") Long seatId
    ) {
        ReserveSeatService.Output output = reserveSeatService.execute(concertScheduleId, seatId, userId);
        return ResponseEntity.ok(ReserveSeatResponse.fromOutput(output)).status(HttpStatus.CREATED).build();
    }

    @Override
    @ConcertTokenRequired
    @PostMapping("/{reservationId}/purchase")
    public ResponseEntity<PurchaseSeatResponse> purchaseSeat(
            @RequestHeader(SharedHttpHeader.X_USER_ID) Long userId,
            @RequestHeader(SharedHttpHeader.X_QUEUE_TOKEN) String queueToken,
            @PathVariable("reservationId") Long reservationId,
            @Valid @RequestBody PurchaseSeatRequest request
    ) {
        PurchaseSeatService.Output output = purchaseSeatService.execute(reservationId, userId, request.toInput());
        return ResponseEntity.status(HttpStatus.CREATED).body(PurchaseSeatResponse.fromOutput(output));
    }
}
