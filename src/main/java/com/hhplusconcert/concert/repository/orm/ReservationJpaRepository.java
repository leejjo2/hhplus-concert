package com.hhplusconcert.concert.repository.orm;

import com.hhplusconcert.concert.repository.domain.entity.ReservationEntity;
import com.hhplusconcert.concert.repository.domain.vo.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, String> {
    Optional<ReservationEntity> findById(Long id);

    List<ReservationEntity> findByConcertScheduleIdAndStatusIn(Long concertScheduleId, Set<ReservationStatus> reservationStatusSet);
}
