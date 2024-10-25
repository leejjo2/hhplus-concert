package com.hhplusconcert.concert.repository;

import com.hhplusconcert.concert.repository.domain.ConcertSeat;
import com.hhplusconcert.concert.repository.domain.entity.ConcertSeatEntity;
import com.hhplusconcert.concert.repository.orm.ConcertSeatJpaRepository;
import com.hhplusconcert.shared.error.ApplicationException;
import com.hhplusconcert.shared.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ConcertSeatRepository {
    private final ConcertSeatJpaRepository concertSeatJpaRepository;

    // 여러 콘서트 좌석을 저장하는 메소드
    public void save(ConcertSeat concertSeat) {
        concertSeatJpaRepository.save(ConcertSeatEntity.fromDomain(concertSeat));
    }

    public void saveAll(List<ConcertSeat> concertSeats) {
        concertSeatJpaRepository.saveAll(concertSeats.stream()
                .map(ConcertSeatEntity::fromDomain)
                .collect(Collectors.toList()));
    }

    // 콘서트 스케줄 ID로 모든 콘서트 좌석을 찾는 메소드
    public List<ConcertSeat> findAllByConcertScheduleId(Long concertScheduleId) {
        List<ConcertSeatEntity> seats = concertSeatJpaRepository.findAllByConcertScheduleId(concertScheduleId);
        if (seats.isEmpty()) {
            throw new ApplicationException(ErrorType.Concert.CONCERT_SEAT_NOT_FOUND);
        }
        return seats.stream().map(ConcertSeatEntity::toDomain).collect(Collectors.toList());
    }

    public ConcertSeat findByIdWithLock(Long id) {
        return concertSeatJpaRepository.findByIdWithLock(id)
                .map(ConcertSeatEntity::toDomain)
                .orElseThrow(() -> new ApplicationException(ErrorType.Concert.CONCERT_SEAT_NOT_FOUND));
    }

    public ConcertSeat findById(Long id) {
        return ConcertSeatEntity.toDomain(concertSeatJpaRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorType.Concert.CONCERT_SEAT_NOT_FOUND)));
    }
}
