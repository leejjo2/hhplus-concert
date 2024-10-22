package com.hhplusconcert.concert.usecase;

import com.hhplusconcert.concert.repository.ConcertSeatRepository;
import com.hhplusconcert.concert.repository.ReservationRepository;
import com.hhplusconcert.concert.repository.domain.ConcertSeat;
import com.hhplusconcert.concert.repository.domain.Reservation;
import com.hhplusconcert.concert.repository.domain.vo.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindReservationSeatService {

    private final ConcertSeatRepository concertSeatRepository;
    private final ReservationRepository reservationRepository;

    public List<Output> execute(Long concertScheduleId) {

        Set<ReservationStatus> statusSet = Set.of(ReservationStatus.RESERVED, ReservationStatus.TEMP_RESERVED, ReservationStatus.PAID);
        Set<Long> unavailableSeatIds = reservationRepository.findByConcertScheduleIdAndStatusIn(concertScheduleId, statusSet).stream().map(Reservation::getSeatId).collect(Collectors.toSet());

        List<ConcertSeat> concertSeats = concertSeatRepository.findAllByConcertScheduleId(concertScheduleId);

        List<Output> outputs = concertSeats.stream().map(concertSeat -> {
            boolean available = !unavailableSeatIds.contains(concertSeat.getId());

            return new Output(
                    concertSeat.getId(),
                    concertSeat.getPosition(),
                    concertSeat.getAmount(),
                    available
            );
        }).collect(Collectors.toList());

        return outputs;
    }

    @AllArgsConstructor
    @Getter
    public static class Output {
        Long seatId;
        Integer position;
        Integer price;
        boolean available;
    }
}
