package com.hhplusconcert.concert.repository;

import com.hhplusconcert.concert.repository.domain.ConcertQueue;
import com.hhplusconcert.concert.repository.domain.entity.ConcertQueueEntity;
import com.hhplusconcert.concert.repository.domain.vo.ConcertQueueStatus;
import com.hhplusconcert.concert.repository.orm.ConcertQueueJpaRepository;
import com.hhplusconcert.shared.error.ApplicationException;
import com.hhplusconcert.shared.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ConcertQueueRepository {
    private final ConcertQueueJpaRepository concertQueueJpaRepository;

    public ConcertQueue findById(Long id) {
        return concertQueueJpaRepository.findById(id)
                .map(ConcertQueueEntity::toDomain)
                .orElseThrow(() -> new ApplicationException(ErrorType.WaitingQueue.WAITING_QUEUE_NOT_FOUND));
    }

    public void updateAll(List<ConcertQueue> concertQueues) {
        concertQueueJpaRepository.saveAll(
                concertQueues.stream().map(ConcertQueueEntity::fromDomain).collect(Collectors.toList())
        );
    }

    public void update(ConcertQueue concertQueue) {
        concertQueueJpaRepository.save(ConcertQueueEntity.fromDomain(concertQueue));
    }

    public Long create(ConcertQueue concertQueue) {
        return concertQueueJpaRepository.save(ConcertQueueEntity.fromDomain(concertQueue)).getId();
    }

    public ConcertQueue findByIdForUpdate(Long id) {
        return concertQueueJpaRepository.findByIdForUpdate(id)
                .map(ConcertQueueEntity::toDomain)
                .orElseThrow(() -> new ApplicationException(ErrorType.WaitingQueue.WAITING_QUEUE_NOT_FOUND));
    }

    public List<ConcertQueue> findByConcertScheduleIdAndStatusAndEnteredAtBefore(
            Long concertScheduleId, ConcertQueueStatus status, LocalDateTime enteredAt
    ) {
        return concertQueueJpaRepository.findByConcertScheduleIdAndStatusAndEnteredAtBefore(concertScheduleId, status, enteredAt)
                .stream().map(ConcertQueueEntity::toDomain).collect(Collectors.toList());
    }

    public Long countByConcertScheduleIdAndStatusAndEnteredAtBefore(
            Long concertScheduleId, ConcertQueueStatus status, LocalDateTime enteredAt
    ) {
        return concertQueueJpaRepository.countByConcertScheduleIdAndStatusAndEnteredAtBefore(concertScheduleId, status, enteredAt);
    }

    public ConcertQueue findByToken(String token) {
        return concertQueueJpaRepository.findByToken(token)
                .map(ConcertQueueEntity::toDomain)
                .orElseThrow(() -> new ApplicationException(ErrorType.WaitingQueue.WAITING_QUEUE_NOT_FOUND));
    }

    public List<ConcertQueue> findByConcertScheduleIdAndStatusOrderByEnteredAtAsc(
            Long concertScheduleId, ConcertQueueStatus status, int limit, int offset
    ) {
        Pageable pageable = PageRequest.of(offset, limit);
        return concertQueueJpaRepository.findByConcertScheduleIdAndStatusOrderByEnteredAtAsc(concertScheduleId, status, pageable).getContent()
                .stream().map(ConcertQueueEntity::toDomain).collect(Collectors.toList());
    }

    public List<ConcertQueue> findByStatusAndExpiredAtBefore(
            ConcertQueueStatus status, LocalDateTime now
    ) {
        return concertQueueJpaRepository.findByStatusAndExpiredAtBefore(status, now)
                .stream().map(ConcertQueueEntity::toDomain).collect(Collectors.toList());
    }

}
