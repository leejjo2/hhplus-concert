package com.hhplusconcert.user.interfaces;

import com.hhplusconcert.user.interfaces.dto.request.ChargeCashRequest;
import com.hhplusconcert.user.interfaces.dto.response.ChargeCashResponse;
import com.hhplusconcert.user.interfaces.dto.response.FindCashResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

//@Tag(name = "User", description = "유저 관련 API")
public interface UserControllerInterface {

    @Tag(name = "User Cash", description = "유저 캐시 관련 API")
    @Operation(summary = "유저 캐시 충전", description = "특정 유저의 캐시를 요청된 금액만큼 충전합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "충전 성공",
                    content = @Content(schema = @Schema(implementation = ChargeCashResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    ResponseEntity<ChargeCashResponse> chargeCash(
            @Parameter(description = "유저 ID", required = true) Long userId,
            @Parameter(description = "충전 요청 데이터", required = true,
                    content = @Content(schema = @Schema(implementation = ChargeCashRequest.class))) ChargeCashRequest request
    );

    @Tag(name = "User Cash", description = "유저 캐시 관련 API")
    @Operation(summary = "유저 캐시 잔액 조회", description = "특정 유저의 현재 캐시 잔액을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = FindCashResponse.class))),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    ResponseEntity<FindCashResponse> findCash(
            @Parameter(description = "유저 ID", required = true) Long userId
    );
}
