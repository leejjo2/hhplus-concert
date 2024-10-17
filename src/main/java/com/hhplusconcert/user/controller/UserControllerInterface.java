package com.hhplusconcert.user.controller;

import com.hhplusconcert.user.usecase.ChargeCashService;
import com.hhplusconcert.user.usecase.FindCashService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserControllerInterface {
    @Operation(summary = "유저의 캐시를 충전하는 API", description = "주어진 금액만큼 유저의 캐시를 충전합니다.")
    @PostMapping("/{userId}/cash/charge")
    ResponseEntity<ChargeCashService.Output> chargeCash(
            @PathVariable("userId") Long userId,
            @RequestBody ChargeCashService.Input input
    );

    @Operation(summary = "유저의 캐시 잔액 조회 API", description = "유저의 현재 캐시 잔액을 조회합니다.")
    @GetMapping("/{userId}/cash")
    ResponseEntity<FindCashService.Output> findCash(
            @PathVariable("userId") Long userId
    );
}
