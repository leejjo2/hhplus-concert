package com.hhplusconcert.concert.controller;

import com.hhplusconcert.concert.aop.ConcertTokenRequired;
import com.hhplusconcert.concert.usecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

public interface ConcertControllerInterface {
    @Operation(summary = "대기열 토큰 생성", description = "특정 콘서트 일정에 대한 대기열 토큰을 생성합니다.")
    @PostMapping("/{concertScheduleId}/queue-token")
    ResponseEntity<CreateQueueUseCase.Output> createQueue(
            @Parameter(description = "콘서트 일정 ID", required = true)
            @PathVariable("concertScheduleId") Long concertScheduleId,
            @RequestBody CreateQueueUseCase.Input input
    );

    @Operation(summary = "대기열 조회", description = "특정 콘서트 일정에 대한 대기열 정보를 조회합니다.")
    @GetMapping("/{concertScheduleId}/queue")
    @ConcertTokenRequired
    ResponseEntity<FindQueueUseCase.Output> findQueue(
            @Parameter(description = "콘서트 일정 ID", required = true)
            @PathVariable("concertScheduleId") Long concertScheduleId
    );

    @Operation(summary = "예약 가능한 날짜 조회", description = "예약 가능한 날짜 목록을 조회합니다.")
    @GetMapping("/{concertScheduleId}/reservation/date")
    ResponseEntity<FindReservationDateUseCase.Output> findReservationDate(
            @Parameter(description = "콘서트 일정 ID", required = true)
            @PathVariable("concertScheduleId") Long concertScheduleId,
            @Parameter(description = "예약 상태 필터", required = true)
            @RequestParam(value = "status") String status
    );

    @Operation(summary = "좌석 상태 조회", description = "특정 콘서트 일정에 대한 좌석 상태를 조회합니다.")
    @GetMapping("/{concertScheduleId}/reservation/seat")
    ResponseEntity<List<FindReservationSeatUseCase.Output>> findReservationSeat(
            @Parameter(description = "콘서트 일정 ID", required = true)
            @PathVariable("concertScheduleId") Long concertScheduleId,
            @Parameter(description = "콘서트 시작 날짜", required = true)
            @RequestParam(value = "concertOpenDate") LocalDate concertOpenDate
    );

    @Operation(summary = "좌석 예약", description = "특정 콘서트 일정의 좌석을 예약합니다.")
    @PostMapping("/{concertScheduleId}/reservation/seat/{seatId}")
    ResponseEntity<Void> reserveSeat(
            @Parameter(description = "콘서트 일정 ID", required = true)
            @PathVariable("concertScheduleId") Long concertScheduleId,
            @Parameter(description = "좌석 ID", required = true)
            @PathVariable("seatId") Long seatId
    );

    @Operation(summary = "좌석 구매", description = "대기열 토큰을 사용하여 좌석을 구매합니다.")
    @PostMapping("/{concertScheduleId}/purchase/seat/{seatId}")
    ResponseEntity<PurchaseSeatUseCase.Output> purchaseSeat(
            @Parameter(description = "콘서트 일정 ID", required = true)
            @PathVariable("concertScheduleId") Long concertScheduleId,
            @Parameter(description = "좌석 ID", required = true)
            @PathVariable("seatId") Long seatId,
            @RequestBody PurchaseSeatUseCase.Input input
    );
}
