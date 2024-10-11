package com.hhplusconcert.concert.usecase;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class FindReservationDateUseCase {
    @AllArgsConstructor
    @Getter
    public static class Output {
        List<LocalDate> date;
    }

}
