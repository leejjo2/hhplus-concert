package com.hhplusconcert.user.interfaces;

import com.hhplusconcert.shared.SharedHttpHeader;
import com.hhplusconcert.user.interfaces.dto.request.ChargeCashRequest;
import com.hhplusconcert.user.interfaces.dto.response.ChargeCashResponse;
import com.hhplusconcert.user.interfaces.dto.response.FindCashResponse;
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

    @PostMapping("/cash/charge")
    @Override
    public ResponseEntity<ChargeCashResponse> chargeCash(
            @RequestHeader(SharedHttpHeader.X_USER_ID) Long userId,
            @RequestBody ChargeCashRequest request
    ) {
        ChargeCashService.Output output = chargeCashService.execute(userId, request.toInput());
        return ResponseEntity.status(HttpStatus.CREATED).body(ChargeCashResponse.fromOutput(output));
    }

    @GetMapping("/cash")
    @Override
    public ResponseEntity<FindCashResponse> findCash(
            @RequestHeader(SharedHttpHeader.X_USER_ID) Long userId
    ) {
        FindCashService.Output output = findCashService.execute(userId);
        return ResponseEntity.ok(FindCashResponse.fromOutput(output));
    }
}
