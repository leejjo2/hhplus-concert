package com.hhplusconcert.concert.usecase;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class FindQueueUseCase {
    @AllArgsConstructor
    @Getter
    public static class Output {
        Integer waitingNumber;
    }
}
