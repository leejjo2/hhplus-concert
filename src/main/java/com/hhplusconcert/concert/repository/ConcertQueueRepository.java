package com.hhplusconcert.concert.repository;

import com.hhplusconcert.concert.repository.domain.ConcertQueue;
import com.hhplusconcert.concert.repository.domain.entity.ConcertQueueEntity;
import com.hhplusconcert.concert.repository.domain.vo.ConcertQueueStatus;
import com.hhplusconcert.concert.repository.orm.ConcertQueueJpaRepository;
import lombok.RequiredArgsConstructor;
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
                .orElseThrow(() -> new RuntimeException("ID가 " + id + "인 대기열 정보를 찾을 수 없습니다."));
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
                .orElseThrow(() -> new RuntimeException("ID가 " + id + "인 대기열 정보를 업데이트하기 위해 찾을 수 없습니다."));
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
                .orElseThrow(() -> new RuntimeException("토큰이 " + token + "인 대기열 정보를 찾을 수 없습니다."));
    }

    public List<ConcertQueue> findByConcertScheduleIdAndStatusOrderByEnteredAtAsc(
            Long concertScheduleId, ConcertQueueStatus status, int limit, int offset
    ) {
        return concertQueueJpaRepository.findByConcertScheduleIdAndStatusOrderByEnteredAtAsc(concertScheduleId, status, limit, offset)
                .stream().map(ConcertQueueEntity::toDomain).collect(Collectors.toList());
    }

    public List<ConcertQueue> findByStatusAndExpiredAtBefore(
            ConcertQueueStatus status, LocalDateTime now
    ) {
        return concertQueueJpaRepository.findByStatusAndExpiredAtBefore(status, now)
                .stream().map(ConcertQueueEntity::toDomain).collect(Collectors.toList());
    }

}
