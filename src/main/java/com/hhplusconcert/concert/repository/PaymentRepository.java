package com.hhplusconcert.concert.repository;

import com.hhplusconcert.concert.repository.domain.Payment;
import com.hhplusconcert.concert.repository.domain.entity.PaymentEntity;
import com.hhplusconcert.concert.repository.orm.PaymentJpaRepository;
import com.hhplusconcert.shared.error.ApplicationException;
import com.hhplusconcert.shared.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepository {
    private final PaymentJpaRepository paymentJpaRepository;

    // ID로 결제를 찾는 메소드
    public Payment findById(Long id) {
        return paymentJpaRepository.findById(id)
                .map(PaymentEntity::toDomain)
                .orElseThrow(() -> new ApplicationException(ErrorType.Payment.PAYMENT_NOT_FOUND));
    }

    // 결제를 저장하는 메소드
    public Payment save(Payment payment) {
        return PaymentEntity.toDomain(paymentJpaRepository.save(PaymentEntity.fromDomain(payment)));
    }
}
