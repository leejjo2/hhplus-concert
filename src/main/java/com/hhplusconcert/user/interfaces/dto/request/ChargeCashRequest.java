package com.hhplusconcert.user.interfaces.dto.request;

import com.hhplusconcert.user.usecase.ChargeCashService;
import lombok.Builder;

@Builder
public record ChargeCashRequest(
        Integer amount
) {
    public ChargeCashService.Input toInput() {
        ChargeCashService.Input input = new ChargeCashService.Input(
                this.amount
        );
        return input;
    }
}
