package com.hhplusconcert.concert.repository.orm;

import com.hhplusconcert.concert.repository.domain.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, String> {
    Optional<PaymentEntity> findById(Long id);
}
