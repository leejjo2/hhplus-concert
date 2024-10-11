package com.hhplusconcert.user.usecase;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class FindCashUseCase {

    @Getter
    @AllArgsConstructor
    public static class Output{
        Integer cash;
    }
}
