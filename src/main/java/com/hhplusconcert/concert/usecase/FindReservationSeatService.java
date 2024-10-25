package com.hhplusconcert.concert.usecase;

import com.hhplusconcert.concert.repository.ConcertSeatRepository;
import com.hhplusconcert.concert.repository.ReservationRepository;
import com.hhplusconcert.concert.repository.domain.ConcertSeat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindReservationSeatService {

    private final ConcertSeatRepository concertSeatRepository;
    private final ReservationRepository reservationRepository;

    public List<Output> execute(Long concertScheduleId) {

        List<ConcertSeat> concertSeats = concertSeatRepository.findAllByConcertScheduleId(concertScheduleId);
        List<Output> outputs = concertSeats.stream().map(concertSeat -> new Output(
                concertSeat.getId(),
                concertSeat.getPosition(),
                concertSeat.getAmount(),
                concertSeat.getIsReserved()
        )).collect(Collectors.toList());

        return outputs;
    }

    @AllArgsConstructor
    @Getter
    public static class Output {
        Long seatId;
        Integer position;
        Integer price;
        boolean isReserved;
    }
}
