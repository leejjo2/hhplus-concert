package com.hhplusconcert.concert.repository;

import com.hhplusconcert.concert.repository.domain.Concert;
import com.hhplusconcert.concert.repository.domain.entity.ConcertEntity;
import com.hhplusconcert.concert.repository.orm.ConcertJpaRepository;
import com.hhplusconcert.shared.error.ApplicationException;
import com.hhplusconcert.shared.error.ErrorType;
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
                .orElseThrow(() -> new ApplicationException(ErrorType.Concert.CONCERT_NOT_FOUND));
    }
}
