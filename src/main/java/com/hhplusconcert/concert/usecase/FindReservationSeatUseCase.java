package com.hhplusconcert.concert.usecase;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class FindReservationSeatUseCase {
    @AllArgsConstructor
    @Getter
    public static class Output {
        Long id;
        Integer position;
        Integer price;
        String status;
    }
}
