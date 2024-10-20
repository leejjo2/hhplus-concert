package com.hhplusconcert.concert.repository.orm;

import com.hhplusconcert.concert.repository.domain.entity.ConcertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConcertJpaRepository extends JpaRepository<ConcertEntity, String> {
    Optional<ConcertEntity> findById(Long id);
}
