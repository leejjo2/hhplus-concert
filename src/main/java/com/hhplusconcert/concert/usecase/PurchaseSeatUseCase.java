package com.hhplusconcert.concert.usecase;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PurchaseSeatUseCase {
    @Getter
    @AllArgsConstructor
    public static class Input {
        LocalDate concertOpenDate;
        Integer purchaseAmount;
    }

    @AllArgsConstructor
    @Getter
    public static class Output {
        Long purchaseNumber;
        String concertName;
        LocalDate concertOpenDate;
        Integer seatPosition;
        Integer purchaseAmount;
        LocalDateTime purchaseDate;
    }
}
