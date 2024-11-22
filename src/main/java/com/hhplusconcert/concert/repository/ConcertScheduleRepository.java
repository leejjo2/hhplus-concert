package com.hhplusconcert.concert.repository;

import com.hhplusconcert.concert.repository.domain.ConcertSchedule;
import com.hhplusconcert.concert.repository.domain.entity.ConcertScheduleEntity;
import com.hhplusconcert.concert.repository.domain.vo.ConcertScheduleStatus;
import com.hhplusconcert.concert.repository.orm.ConcertScheduleJpaRepository;
import com.hhplusconcert.shared.error.ApplicationException;
import com.hhplusconcert.shared.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ConcertScheduleRepository {
    private final ConcertScheduleJpaRepository concertScheduleJpaRepository;

    public List<ConcertSchedule> findAllByStatus(ConcertScheduleStatus concertScheduleStatus) {
        return concertScheduleJpaRepository.findAllByStatus(concertScheduleStatus)
                .stream()
                .map(ConcertScheduleEntity::toDomain)
                .collect(Collectors.toList());
    }

    public List<ConcertSchedule> findAllByConcertIdAndStatus(Long id, ConcertScheduleStatus concertScheduleStatus) {
        return concertScheduleJpaRepository.findAllByConcertIdAndStatus(id, concertScheduleStatus)
                .stream()
                .map(ConcertScheduleEntity::toDomain)
                .collect(Collectors.toList());
    }

    public void saveAll(List<ConcertSchedule> concertSchedules) {
        concertScheduleJpaRepository.saveAll(concertSchedules.stream()
                .map(ConcertScheduleEntity::fromDomain)
                .collect(Collectors.toList()));
    }

    public void save(ConcertSchedule concertSchedule) {
        concertScheduleJpaRepository.save(ConcertScheduleEntity.fromDomain(concertSchedule));
    }

    public ConcertSchedule findById(Long id) {
        return concertScheduleJpaRepository.findById(id)
                .map(ConcertScheduleEntity::toDomain)
                .orElseThrow(() -> new ApplicationException(ErrorType.Concert.CONCERT_SCHEDULE_NOT_FOUND));
    }
}
