package com.hhplusconcert.concert.interfaces.dto.request;

import com.hhplusconcert.concert.usecase.PurchaseSeatService;
import lombok.Builder;

@Builder
public record PurchaseSeatRequest(
        Integer purchaseAmount
) {
    public PurchaseSeatService.Input toInput() {
        PurchaseSeatService.Input input = new PurchaseSeatService.Input(
                this.purchaseAmount
        );
        return input;
    }
}
