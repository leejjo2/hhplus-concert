package com.hhplusconcert.user.repository.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String userId;
    private int amount;

    public void deductAmount(int amountToDeduct) {
        if (amountToDeduct <= 0) {
            throw new RuntimeException("차감 금액은 0보다 커야 합니다.");
        }

        if (this.amount < amountToDeduct) {
            throw new RuntimeException("잔액이 부족합니다.");
        }

        this.amount -= amountToDeduct;
    }

    public void chargeAmount(int amountToCharge) {
        if (amountToCharge <= 0) {
            throw new RuntimeException("충전 금액은 0보다 커야 합니다.");
        }

        this.amount += amountToCharge;
    }
}
