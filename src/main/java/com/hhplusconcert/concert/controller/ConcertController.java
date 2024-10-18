package com.hhplusconcert.concert.controller;

import com.hhplusconcert.concert.usecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/concerts")
public class ConcertController {

    @Operation(summary = "대기열 토큰 생성", description = "특정 콘서트 일정에 대한 대기열 토큰을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "토큰 생성 성공", content = @io.swagger.v3.oas.annotations.media.Content(
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = CreateQueueUseCase.Output.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/{concertScheduleId}/queue-token")
    public ResponseEntity<CreateQueueUseCase.Output> createQueue(
            @Parameter(description = "콘서트 일정 ID", required = true)
            @PathVariable("concertScheduleId") Long concertScheduleId,
            @RequestBody CreateQueueUseCase.Input input
    ) {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl9pZCI6InRva2VuX2lkXzEiLCJ1c2VySWQiOiJleGFtcGxlVXNlciIsImV4cCI6MTcyODYxMjY5Niwid2F0aW5nX251bWJlciI6MTIzNH0.8PooXzqNvBdkgXt6GnMaBGOdSyMvl8ziPdl5M4QSvR4";
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateQueueUseCase.Output(token));
    }

    @Operation(summary = "대기열 조회", description = "특정 콘서트 일정에 대한 대기열 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "대기열 조회 성공", content = @io.swagger.v3.oas.annotations.media.Content(
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = FindQueueUseCase.Output.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/{concertScheduleId}/queue")
    public ResponseEntity<FindQueueUseCase.Output> findQueue(
            @Parameter(description = "사용자 인증 토큰", required = true)
            @RequestHeader("Authorization") String authorizationToken,
            @Parameter(description = "콘서트 일정 ID", required = true)
            @PathVariable("concertScheduleId") Long concertScheduleId
    ) {
        int waitingNumber = 10; // 실제 대기 번호 조회 로직 필요
        return ResponseEntity.ok(new FindQueueUseCase.Output(waitingNumber));
    }

    @Operation(summary = "예약 가능한 날짜 조회", description = "예약 가능한 날짜 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "예약 가능한 날짜 조회 성공", content = @io.swagger.v3.oas.annotations.media.Content(
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = FindReservationDateUseCase.Output.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/{concertScheduleId}/reservation/date")
    public ResponseEntity<FindReservationDateUseCase.Output> findReservationDate(
            @Parameter(description = "사용자 인증 토큰", required = true)
            @RequestHeader("Authorization") String authorizationToken,
            @Parameter(description = "콘서트 일정 ID", required = true)
            @PathVariable("concertScheduleId") Long concertScheduleId,
            @Parameter(description = "예약 상태 필터", required = true)
            @RequestParam(value = "status") String status
    ) {
        List<LocalDate> availableDates = List.of(
                LocalDate.parse("2024-10-09"),
                LocalDate.parse("2024-10-10")
        );
        return ResponseEntity.ok(new FindReservationDateUseCase.Output(availableDates));
    }

    @Operation(summary = "좌석 상태 조회", description = "특정 콘서트 일정에 대한 좌석 상태를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좌석 상태 조회 성공", content = @io.swagger.v3.oas.annotations.media.Content(
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = FindReservationSeatUseCase.Output.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/{concertScheduleId}/reservation/seat")
    public ResponseEntity<List<FindReservationSeatUseCase.Output>> findReservationSeat(
            @Parameter(description = "사용자 인증 토큰", required = true)
            @RequestHeader("Authorization") String authorizationToken,
            @Parameter(description = "콘서트 일정 ID", required = true)
            @PathVariable("concertScheduleId") Long concertScheduleId,
            @Parameter(description = "콘서트 시작 날짜", required = true)
            @RequestParam(value = "concertOpenDate") LocalDate concertOpenDate
    ) {
        List<FindReservationSeatUseCase.Output> seats = List.of(
                new FindReservationSeatUseCase.Output(1L, 1, 10000, "AVAILABLE"),
                new FindReservationSeatUseCase.Output(2L, 2, 10000, "TEMP_RESERVED"),
                new FindReservationSeatUseCase.Output(3L, 3, 10000, "RESERVED")
        );
        return ResponseEntity.ok(seats);
    }

    @Operation(summary = "좌석 예약", description = "특정 콘서트 일정의 좌석을 예약합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "좌석 예약 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/{concertScheduleId}/reservation/seat/{seatId}")
    public ResponseEntity<Void> reserveSeat(
            @Parameter(description = "사용자 인증 토큰", required = true)
            @RequestHeader("Authorization") String authorizationToken,
            @Parameter(description = "콘서트 일정 ID", required = true)
            @PathVariable("concertScheduleId") Long concertScheduleId,
            @Parameter(description = "좌석 ID", required = true)
            @PathVariable("seatId") Long seatId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "좌석 구매", description = "대기열 토큰을 사용하여 좌석을 구매합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "좌석 구매 성공", content = @io.swagger.v3.oas.annotations.media.Content(
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PurchaseSeatUseCase.Output.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/{concertScheduleId}/purchase/seat/{seatId}")
    public ResponseEntity<PurchaseSeatUseCase.Output> purchaseSeat(
            @Parameter(description = "대기열 토큰", required = true)
            @RequestHeader("Authorization") String queueToken,
            @Parameter(description = "콘서트 일정 ID", required = true)
            @PathVariable("concertScheduleId") Long concertScheduleId,
            @Parameter(description = "좌석 ID", required = true)
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
