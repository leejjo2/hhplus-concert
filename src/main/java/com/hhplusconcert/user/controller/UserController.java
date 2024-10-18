package com.hhplusconcert.user.controller;

import com.hhplusconcert.user.usecase.ChargeCashUseCase;
import com.hhplusconcert.user.usecase.FindCashUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Operation(summary = "유저의 캐시를 충전하는 API", description = "주어진 금액만큼 유저의 캐시를 충전합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "캐시 충전 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/{userId}/cash/charge")
    public ResponseEntity<ChargeCashUseCase.Output> chargeCash(
            @PathVariable("userId") Long userId,
            @RequestBody ChargeCashUseCase.Input input
    ) {
        // 캐시 충전 로직 필요
        int newBalance = 200000; // 새로운 잔액 예시 (실제 로직에서 계산 필요)
        return ResponseEntity.status(HttpStatus.CREATED).body(new ChargeCashUseCase.Output(newBalance));
    }

    @Operation(summary = "유저의 캐시 잔액 조회 API", description = "유저의 현재 캐시 잔액을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "캐시 조회 성공"),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/{userId}/cash")
    public ResponseEntity<FindCashUseCase.Output> findCash(
            @PathVariable("userId") Long userId
    ) {
        // 캐시 조회 로직 필요
        int currentBalance = 10000; // 현재 잔액 예시 (실제 로직에서 조회 필요)
        return ResponseEntity.ok(new FindCashUseCase.Output(currentBalance));
    }
}
