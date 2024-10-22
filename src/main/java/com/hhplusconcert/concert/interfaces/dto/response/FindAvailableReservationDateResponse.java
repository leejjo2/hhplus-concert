package com.hhplusconcert.concert.interfaces.dto.response;

import com.hhplusconcert.concert.usecase.FindAvailableReservationDateService;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record FindAvailableReservationDateResponse(
        List<LocalDate> date
) {
    public static FindAvailableReservationDateResponse fromOutput(FindAvailableReservationDateService.Output output) {
        FindAvailableReservationDateResponse response = FindAvailableReservationDateResponse.builder()
                .date(output.getDate())
                .build();
        return response;
    }
}