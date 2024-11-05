package com.hhplusconcert.concert.repository.orm;

import com.hhplusconcert.concert.repository.domain.entity.ConcertQueueEntity;
import com.hhplusconcert.concert.repository.domain.vo.ConcertQueueStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ConcertQueueJpaRepository extends JpaRepository<ConcertQueueEntity, String> {
    List<ConcertQueueEntity> findAll();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT cq FROM ConcertQueueEntity cq WHERE cq.id = ?1")
    Optional<ConcertQueueEntity> findByIdForUpdate(Long id);

    Optional<ConcertQueueEntity> findById(Long id);

    List<ConcertQueueEntity> findByConcertScheduleIdAndStatusAndEnteredAtBefore(
            Long concertScheduleId,
            ConcertQueueStatus status,
            LocalDateTime enteredAt
    );

    Long countByConcertScheduleIdAndStatusAndEnteredAtBefore(Long concertScheduleId, ConcertQueueStatus status, LocalDateTime enteredAt);

    Optional<ConcertQueueEntity> findByToken(String token);

    Page<ConcertQueueEntity> findByConcertScheduleIdAndStatusOrderByEnteredAtAsc(
            Long concertScheduleId,
            ConcertQueueStatus status,
            Pageable pageable
    );

    List<ConcertQueueEntity> findByStatusAndExpiredAtBefore(
            ConcertQueueStatus status,
            LocalDateTime now
    );
}
