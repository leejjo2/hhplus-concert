package com.hhplusconcert.concert.interfaces.dto.response;

import com.hhplusconcert.concert.usecase.CreateQueueService;
import lombok.Builder;

@Builder
public record CreateQueueResponse(
        String token
) {
    public static CreateQueueResponse fromOutput(CreateQueueService.Output output) {
        CreateQueueResponse response = CreateQueueResponse.builder()
                .token(output.getToken())
                .build();
        return response;
    }
}
