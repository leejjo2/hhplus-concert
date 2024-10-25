package com.hhplusconcert.concert.repository.orm;

import com.hhplusconcert.concert.repository.domain.entity.ConcertSeatEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConcertSeatJpaRepository extends JpaRepository<ConcertSeatEntity, String> {

    List<ConcertSeatEntity> findAllByConcertScheduleId(Long concertScheduleId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select cs from ConcertSeatEntity cs where cs.id = :id")
    Optional<ConcertSeatEntity> findByIdWithLock(Long id);

    Optional<ConcertSeatEntity> findById(Long id);
}
