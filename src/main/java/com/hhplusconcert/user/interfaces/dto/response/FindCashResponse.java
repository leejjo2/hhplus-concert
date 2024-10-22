package com.hhplusconcert.user.interfaces.dto.response;

import com.hhplusconcert.user.usecase.FindCashService;
import lombok.Builder;

@Builder
public record FindCashResponse(
        Integer cash
) {
    public static FindCashResponse fromOutput(FindCashService.Output output) {
        FindCashResponse response = FindCashResponse.builder()
                .cash(output.getCash())
                .build();
        return response;
    }
}
