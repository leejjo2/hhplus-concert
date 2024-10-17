package com.hhplusconcert.concert.repository;

import com.hhplusconcert.concert.repository.domain.ConcertSchedule;
import com.hhplusconcert.concert.repository.domain.entity.ConcertScheduleEntity;
import com.hhplusconcert.concert.repository.domain.vo.ConcertScheduleStatus;
import com.hhplusconcert.concert.repository.orm.ConcertScheduleJpaRepository;
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

    public List<ConcertSchedule> findAllByIdAndStatus(Long id, ConcertScheduleStatus concertScheduleStatus) {
        return concertScheduleJpaRepository.findAllByIdAndStatus(id, concertScheduleStatus)
                .stream()
                .map(ConcertScheduleEntity::toDomain)
                .collect(Collectors.toList());
    }

    public void saveAll(List<ConcertSchedule> concertSchedules) {
        concertScheduleJpaRepository.saveAll(concertSchedules.stream()
                .map(ConcertScheduleEntity::fromDomain)
                .collect(Collectors.toList()));
    }

    public ConcertSchedule findById(Long id) {
        return concertScheduleJpaRepository.findById(id)
                .map(ConcertScheduleEntity::toDomain)
                .orElseThrow(() -> new RuntimeException("ID가 " + id + "인 콘서트 스케줄을 찾을 수 없습니다."));
    }
}
