package com.hhplusconcert.concert.usecase;

import com.hhplusconcert.concert.repository.ConcertScheduleRepository;
import com.hhplusconcert.concert.repository.domain.ConcertSchedule;
import com.hhplusconcert.concert.repository.domain.vo.ConcertScheduleStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindAvailableReservationDateService {

    private final ConcertScheduleRepository concertScheduleRepository;

    public Output execute(Long concertId) {
        List<ConcertSchedule> availableConcertSchedules = concertScheduleRepository.findAllByConcertIdAndStatus(concertId, ConcertScheduleStatus.AVAILABLE);
        return new Output(availableConcertSchedules.stream().map(ConcertSchedule::getOpenDate).collect(Collectors.toList()));
    }

    @AllArgsConstructor
    @Getter
    public static class Output {
        List<LocalDate> date;
    }

}
