package com.hhplusconcert.concert.repository;

import com.hhplusconcert.concert.repository.domain.Reservation;
import com.hhplusconcert.concert.repository.domain.entity.ReservationEntity;
import com.hhplusconcert.concert.repository.domain.vo.ReservationStatus;
import com.hhplusconcert.concert.repository.orm.ReservationJpaRepository;
import com.hhplusconcert.shared.error.ApplicationException;
import com.hhplusconcert.shared.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {
    private final ReservationJpaRepository reservationJpaRepository;

    // 예약을 저장하는 메소드
    public Reservation save(Reservation reservation) {
        return ReservationEntity.toDomain(reservationJpaRepository.save(ReservationEntity.fromDomain(reservation)));
    }

    // 특정 콘서트 스케줄 ID와 상태 집합에 따른 예약을 찾는 메소드
    public List<Reservation> findByConcertScheduleIdAndStatusIn(Long concertScheduleId, Set<ReservationStatus> statusSet) {
        return reservationJpaRepository.findByConcertScheduleIdAndStatusIn(concertScheduleId, statusSet)
                .stream()
                .map(ReservationEntity::toDomain)
                .collect(Collectors.toList());
    }

    public List<Reservation> findByStatusAndReservedAtBefore(ReservationStatus status, LocalDateTime reservedAt) {
        return reservationJpaRepository.findByStatusAndReservedAtBeforeWithLock(status, reservedAt)
                .stream()
                .map(ReservationEntity::toDomain)
                .collect(Collectors.toList());
    }

    // ID로 예약을 찾는 메소드
    public Reservation findById(Long id) {
        return reservationJpaRepository.findById(id)
                .map(ReservationEntity::toDomain)
                .orElseThrow(() -> new ApplicationException(ErrorType.Concert.RESERVATION_NOT_FOUND));
    }
}
