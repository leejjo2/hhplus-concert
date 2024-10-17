package com.hhplusconcert.user.controller;

import com.hhplusconcert.user.usecase.ChargeCashService;
import com.hhplusconcert.user.usecase.FindCashService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController implements UserControllerInterface {

    private final ChargeCashService chargeCashService;
    private final FindCashService findCashService;

    @PostMapping("/{userId}/cash/charge")
    @Override
    public ResponseEntity<ChargeCashService.Output> chargeCash(
            @PathVariable("userId") Long userId,
            @RequestBody ChargeCashService.Input input
    ) {
        ChargeCashService.Output output = chargeCashService.execute(userId, input);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @GetMapping("/{userId}/cash")
    @Override
    public ResponseEntity<FindCashService.Output> findCash(
            @PathVariable("userId") Long userId
    ) {
        FindCashService.Output output = findCashService.execute(userId);
        return ResponseEntity.ok(output);
    }
}
