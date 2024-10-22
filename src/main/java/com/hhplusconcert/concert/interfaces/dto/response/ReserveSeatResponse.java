package com.hhplusconcert.concert.interfaces.dto.response;

import com.hhplusconcert.concert.usecase.ReserveSeatService;
import lombok.Builder;

@Builder
public record ReserveSeatResponse(
        Long reservationId
) {
    public static ReserveSeatResponse fromOutput(ReserveSeatService.Output output) {
        ReserveSeatResponse response = ReserveSeatResponse.builder()
                .reservationId(output.getReservationId())
                .build();
        return response;
    }
}
