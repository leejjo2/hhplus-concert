package com.hhplusconcert.user.usecase;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ChargeCashUseCase {

    @Getter
    @AllArgsConstructor
    public static class Input{
        Integer amount;
    }

    @Getter
    @AllArgsConstructor
    public static class Output{
        Integer cash;
    }
}
