package com.hhplusconcert.concert.interfaces.dto.response;

import com.hhplusconcert.concert.usecase.PurchaseSeatService;
import lombok.Builder;

@Builder
public record PurchaseSeatResponse(
        Long purchaseNumber
) {
    public static PurchaseSeatResponse fromOutput(PurchaseSeatService.Output output) {
        PurchaseSeatResponse response = PurchaseSeatResponse.builder()
                .purchaseNumber(output.getPurchaseNumber())
                .build();
        return response;
    }
}
