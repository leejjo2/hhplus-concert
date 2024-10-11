package com.hhplusconcert.concert.usecase;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class CreateQueueUseCase {

    @Getter
    @AllArgsConstructor
    public static class Input {
        Long userId;
    }

    @AllArgsConstructor
    @Getter
    public static class Output {
        String token;
    }
}
