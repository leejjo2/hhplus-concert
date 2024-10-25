package com.hhplusconcert.concert.interfaces.dto.response;

import com.hhplusconcert.concert.usecase.FindReservationSeatService;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record FindReservationSeatResponse(
        Long seatId,
        Integer position,
        Integer price,
        boolean isReserved
) {
    public static List<FindReservationSeatResponse> fromOutput(List<FindReservationSeatService.Output> outputs) {
        List<FindReservationSeatResponse> response = outputs.stream().map(output -> {
            FindReservationSeatResponse res = FindReservationSeatResponse.builder()
                    .seatId(output.getSeatId())
                    .position(output.getPosition())
                    .price(output.getPrice())
                    .isReserved(output.isReserved())
                    .build();
            return res;
        }).collect(Collectors.toList());
        return response;
    }
}
