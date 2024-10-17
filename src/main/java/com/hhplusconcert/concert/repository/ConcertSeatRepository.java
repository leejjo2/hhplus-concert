package com.hhplusconcert.concert.repository;

import com.hhplusconcert.concert.repository.domain.ConcertSeat;
import com.hhplusconcert.concert.repository.domain.entity.ConcertSeatEntity;
import com.hhplusconcert.concert.repository.orm.ConcertSeatJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ConcertSeatRepository {
    private final ConcertSeatJpaRepository concertSeatJpaRepository;

    // 여러 콘서트 좌석을 저장하는 메소드
    public void saveAll(List<ConcertSeat> concertSeats) {
        concertSeatJpaRepository.saveAll(concertSeats.stream()
                .map(ConcertSeatEntity::fromDomain)
                .collect(Collectors.toList()));
    }

    // 콘서트 스케줄 ID로 모든 콘서트 좌석을 찾는 메소드
    public List<ConcertSeat> findAllByConcertScheduleId(Long concertScheduleId) {
        List<ConcertSeatEntity> seats = concertSeatJpaRepository.findAllByConcertScheduleId(concertScheduleId);
        if (seats.isEmpty()) {
            throw new RuntimeException("ID가 " + concertScheduleId + "인 콘서트 스케줄에 해당하는 좌석이 없습니다.");
        }
        return seats.stream().map(ConcertSeatEntity::toDomain).collect(Collectors.toList());
    }
}
