package com.hhplusconcert.concert.interfaces;

import com.hhplusconcert.concert.interfaces.dto.request.PurchaseSeatRequest;
import com.hhplusconcert.concert.interfaces.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

//@Tag(name = "Concert", description = "콘서트 관련 API")
public interface ConcertControllerInterface {

    @Tag(name = "Queue", description = "대기열 관련 API")
    @Operation(
            summary = "대기열 토큰 생성",
            description = "특정 콘서트 일정에 대해 사용자의 대기열 토큰을 생성합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "대기열 토큰 생성 성공",
                    content = @Content(schema = @Schema(implementation = CreateQueueResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "콘서트 일정 찾을 수 없음", content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<CreateQueueResponse> createQueue(
            @Parameter(description = "유저 ID", required = true, example = "12345") Long userId,
            @Parameter(description = "콘서트 일정 ID", required = true, example = "98765") Long concertScheduleId
    );

    @Tag(name = "Queue", description = "대기열 관련 API")
    @Operation(
            summary = "대기열 조회",
            description = "주어진 대기열 토큰과 콘서트 일정 ID로 대기열 정보를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "대기열 정보 조회 성공",
                    content = @Content(schema = @Schema(implementation = FindQueueResponse.class))),
            @ApiResponse(responseCode = "404", description = "대기열 정보를 찾을 수 없음", content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<FindQueueResponse> findQueue(
            @Parameter(description = "대기열 토큰", required = true, example = "abcde12345") String queueToken,
            @Parameter(description = "콘서트 일정 ID", required = true, example = "98765") Long concertScheduleId
    );

    @Tag(name = "Reservation", description = "예약 관련 API")
    @Operation(
            summary = "예약 가능한 날짜 조회",
            description = "특정 콘서트 일정에 대한 예약 가능한 날짜 목록을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "예약 가능한 날짜 조회 성공",
                    content = @Content(schema = @Schema(implementation = FindAvailableReservationDateResponse.class))),
            @ApiResponse(responseCode = "404", description = "예약 가능한 날짜를 찾을 수 없음", content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<FindAvailableReservationDateResponse> findReservationDate(
            @Parameter(description = "대기열 토큰", required = true, example = "abcde12345") String queueToken,
            @Parameter(description = "콘서트 일정 ID", required = true, example = "98765") Long concertScheduleId
    );

    @Tag(name = "Seat Reservation", description = "좌석 예약 관련 API")
    @Operation(
            summary = "좌석 상태 조회",
            description = "콘서트 일정에 대한 좌석 예약 상태를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좌석 상태 조회 성공",
                    content = @Content(schema = @Schema(implementation = FindReservationSeatResponse.class))),
            @ApiResponse(responseCode = "404", description = "좌석 상태를 찾을 수 없음", content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<List<FindReservationSeatResponse>> findReservationSeat(
            @Parameter(description = "대기열 토큰", required = true, example = "abcde12345") String queueToken,
            @Parameter(description = "콘서트 일정 ID", required = true, example = "98765") Long concertScheduleId
    );

    @Tag(name = "Seat Reservation", description = "좌석 예약 관련 API")
    @Operation(
            summary = "좌석 예약",
            description = "특정 콘서트 일정에 대해 좌석을 예약합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좌석 예약 성공",
                    content = @Content(schema = @Schema(implementation = ReserveSeatResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "좌석 또는 콘서트 일정을 찾을 수 없음", content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<ReserveSeatResponse> reserveSeat(
            @Parameter(description = "유저 ID", required = true, example = "12345") Long userId,
            @Parameter(description = "대기열 토큰", required = true, example = "abcde12345") String queueToken,
            @Parameter(description = "콘서트 일정 ID", required = true, example = "98765") Long concertScheduleId,
            @Parameter(description = "좌석 ID", required = true, example = "54321") Long seatId
    );

    @Tag(name = "Seat Purchase", description = "좌석 구매 관련 API")
    @Operation(
            summary = "좌석 구매",
            description = "예약된 좌석을 구매합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좌석 구매 성공",
                    content = @Content(schema = @Schema(implementation = PurchaseSeatResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "예약을 찾을 수 없음", content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<PurchaseSeatResponse> purchaseSeat(
            @Parameter(description = "유저 ID", required = true, example = "12345") Long userId,
            @Parameter(description = "대기열 토큰", required = true, example = "abcde12345") String queueToken,
            @Parameter(description = "예약 ID", required = true, example = "76543") Long reservationId,
            @Parameter(description = "구매 요청 데이터", required = true, schema = @Schema(implementation = PurchaseSeatRequest.class))
            PurchaseSeatRequest request
    );
}
