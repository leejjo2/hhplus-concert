package com.hhplusconcert.concert.repository;

import com.hhplusconcert.concert.repository.domain.Concert;
import com.hhplusconcert.concert.repository.domain.entity.ConcertEntity;
import com.hhplusconcert.concert.repository.orm.ConcertJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ConcertRepository {
    private final ConcertJpaRepository concertJpaRepository;

    public void saveAll(List<Concert> concerts) {
        concertJpaRepository.saveAll(concerts.stream()
                .map(ConcertEntity::fromDomain)
                .collect(Collectors.toList()));
    }

    public Concert findById(Long id) {
        return concertJpaRepository.findById(id)
                .map(ConcertEntity::toDomain)
                .orElseThrow(() -> new RuntimeException("ID가 " + id + "인 콘서트를 찾을 수 없습니다."));
    }
}
