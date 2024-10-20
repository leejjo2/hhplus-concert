package com.hhplusconcert.concert.controller;

import com.hhplusconcert.concert.aop.ConcertTokenRequired;
import com.hhplusconcert.concert.usecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ConcertControllerInterface {
    @Operation(summary = "대기열 토큰 생성", description = "특정 콘서트 일정에 대한 대기열 토큰을 생성합니다.")
    @PostMapping("/{concertScheduleId}/queue-token")
    ResponseEntity<CreateQueueService.Output> createQueue(
            @Parameter(description = "콘서트 일정 ID", required = true)
            @PathVariable("concertScheduleId") Long concertScheduleId,
            @RequestBody CreateQueueService.Input input
    );

    @Operation(summary = "대기열 조회", description = "특정 콘서트 일정에 대한 대기열 정보를 조회합니다.")
    @GetMapping("/{concertScheduleId}/queue")
    @ConcertTokenRequired
    ResponseEntity<FindQueueService.Output> findQueue(
            @Parameter(description = "콘서트 일정 ID", required = true)
            @PathVariable("concertScheduleId") Long concertScheduleId
    );

    @Operation(summary = "예약 가능한 날짜 조회", description = "예약 가능한 날짜 목록을 조회합니다.")
    @GetMapping("/{concertScheduleId}/reservation/available-date")
    ResponseEntity<FindAvailableReservationDateService.Output> findReservationDate(
            @Parameter(description = "콘서트 일정 ID", required = true)
            @PathVariable("concertScheduleId") Long concertScheduleId
    );

    @Operation(summary = "좌석 상태 조회", description = "특정 콘서트 일정에 대한 좌석 상태를 조회합니다.")
    @GetMapping("/{concertScheduleId}/reservation/seat")
    ResponseEntity<List<FindReservationSeatService.Output>> findReservationSeat(
            @Parameter(description = "콘서트 일정 ID", required = true)
            @PathVariable("concertScheduleId") Long concertScheduleId
    );

    @Operation(summary = "좌석 예약", description = "특정 콘서트 일정의 좌석을 예약합니다.")
    @PostMapping("/{concertScheduleId}/reservation/seat/{seatId}")
    ResponseEntity<ReserveSeatService.Output> reserveSeat(
            @Parameter(description = "콘서트 일정 ID", required = true)
            @PathVariable("concertScheduleId") Long concertScheduleId,
            @Parameter(description = "좌석 ID", required = true)
            @PathVariable("seatId") Long seatId,
            @RequestBody ReserveSeatService.Input input
    );

    @Operation(summary = "좌석 구매", description = "대기열 토큰을 사용하여 좌석을 구매합니다.")
    @PostMapping("/{reservationId}/purchase")
    ResponseEntity<PurchaseSeatService.Output> purchaseSeat(
            @Parameter(description = "예약 ID", required = true)
            @PathVariable("reservationId") Long reservationId,
            @RequestBody PurchaseSeatService.Input input
    );
}
