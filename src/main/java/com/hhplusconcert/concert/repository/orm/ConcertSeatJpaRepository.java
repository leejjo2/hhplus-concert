package com.hhplusconcert.concert.repository.orm;

import com.hhplusconcert.concert.repository.domain.entity.ConcertSeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertSeatJpaRepository extends JpaRepository<ConcertSeatEntity, String> {

    List<ConcertSeatEntity> findAllByConcertScheduleId(Long concertScheduleId);
}
