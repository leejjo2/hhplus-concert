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
    @Query(value = "SELECT * FROM concert_queue WHERE id = ?1", nativeQuery = true)
    Optional<ConcertQueueEntity> findByIdForUpdate(Long id);

    Optional<ConcertQueueEntity> findById(Long id);

    @Query(value = "SELECT * FROM concert_queue WHERE concert_schedule_id = ?1 AND status = ?2 AND entered_at < ?3", nativeQuery = true)
    List<ConcertQueueEntity> findByConcertScheduleIdAndStatusAndEnteredAtBefore(
            Long concertScheduleId,
            ConcertQueueStatus status,
            LocalDateTime enteredAt
    );

    Long countByConcertScheduleIdAndStatusAndEnteredAtBefore(Long concertScheduleId, ConcertQueueStatus status, LocalDateTime enteredAt);

    Optional<ConcertQueueEntity> findByToken(String token);

    @Query(value = "SELECT * FROM concert_queue WHERE concert_schedule_id = ?1 AND status = ?2 ORDER BY entered_at ASC LIMIT ?3 OFFSET ?4", nativeQuery = true)
    List<ConcertQueueEntity> findByConcertScheduleIdAndStatusOrderByEnteredAtAsc(
            Long concertScheduleId,
            String status,
            int limit,
            int offset
    );

    Page<ConcertQueueEntity> findByConcertScheduleIdAndStatusOrderByEnteredAtAsc(
            Long concertScheduleId,
            ConcertQueueStatus status,
            Pageable pageable
    );

    @Query(value = "SELECT * FROM concert_queue WHERE status = ?1 AND expired_at < ?2", nativeQuery = true)
    List<ConcertQueueEntity> findByStatusAndExpiredAtBefore(
            String status,
            LocalDateTime now
    );
}
