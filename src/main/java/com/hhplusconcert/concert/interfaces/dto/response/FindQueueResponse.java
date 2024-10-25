package com.hhplusconcert.concert.interfaces.dto.response;

import com.hhplusconcert.concert.usecase.FindQueueService;
import lombok.Builder;

@Builder
public record FindQueueResponse(
        Long waitingNumber
) {
    public static FindQueueResponse fromOutput(FindQueueService.Output output) {
        FindQueueResponse response = FindQueueResponse.builder()
                .waitingNumber(output.getWaitingNumber())
                .build();
        return response;
    }
}
