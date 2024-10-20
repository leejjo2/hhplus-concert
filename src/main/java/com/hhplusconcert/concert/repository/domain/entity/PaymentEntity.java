package com.hhplusconcert.concert.repository.domain.entity;

import com.hhplusconcert.concert.repository.domain.Payment;
import com.hhplusconcert.concert.repository.domain.vo.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PAYMENT")
@Entity
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private int price;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private LocalDateTime createdAt;


    public static Payment toDomain(PaymentEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Payment(
                entity.getId(),
                entity.getUserId(),
                entity.getPrice(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }

    public static PaymentEntity fromDomain(Payment domain) {
        if (domain == null) {
            return null;
        }
        return new PaymentEntity(
                domain.getId(),
                domain.getUserId(),
                domain.getPrice(),
                domain.getStatus(),
                domain.getCreatedAt()
        );
    }

}
