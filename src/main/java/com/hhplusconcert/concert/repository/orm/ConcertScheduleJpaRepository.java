package com.hhplusconcert.concert.repository.orm;


import com.hhplusconcert.concert.repository.domain.entity.ConcertScheduleEntity;
import com.hhplusconcert.concert.repository.domain.vo.ConcertScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConcertScheduleJpaRepository extends JpaRepository<ConcertScheduleEntity, String> {
    List<ConcertScheduleEntity> findAllByStatus(ConcertScheduleStatus concertScheduleStatus);

    List<ConcertScheduleEntity> findAllByIdAndStatus(Long id, ConcertScheduleStatus concertScheduleStatus);

    List<ConcertScheduleEntity> findAllByConcertIdAndStatus(Long concertId, ConcertScheduleStatus concertScheduleStatus);

    Optional<ConcertScheduleEntity> findById(Long id);
}
