package com.hhplusconcert.user.interfaces.dto.response;

import com.hhplusconcert.user.usecase.ChargeCashService;
import lombok.Builder;

@Builder
public record ChargeCashResponse(
        Integer cash
) {
    public static ChargeCashResponse fromOutput(ChargeCashService.Output output) {
        ChargeCashResponse response = ChargeCashResponse.builder()
                .cash(output.getCash())
                .build();
        return response;
    }
}
