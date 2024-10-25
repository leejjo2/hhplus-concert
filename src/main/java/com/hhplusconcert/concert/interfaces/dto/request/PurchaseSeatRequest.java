package com.hhplusconcert.concert.interfaces.dto.request;

import com.hhplusconcert.concert.usecase.PurchaseSeatService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PurchaseSeatRequest(
        @NotNull(message = "구매 금액은 필수입니다.")
        @Min(value = 1, message = "구매 금액은 1 이상이어야 합니다.")
        Integer purchaseAmount
) {
    public PurchaseSeatService.Input toInput() {
        return new PurchaseSeatService.Input(this.purchaseAmount);
    }
}
