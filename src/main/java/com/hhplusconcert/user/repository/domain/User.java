package com.hhplusconcert.user.repository.domain;

import com.hhplusconcert.shared.error.ApplicationException;
import com.hhplusconcert.shared.error.ErrorType;
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
            throw new ApplicationException(ErrorType.User.AMOUNT_MUST_BE_POSITIVE);
        }

        if (this.amount < amountToDeduct) {
            throw new ApplicationException(ErrorType.User.NOT_ENOUGH_BALANCE);
        }

        this.amount -= amountToDeduct;
    }

    public void chargeAmount(int amountToCharge) {
        if (amountToCharge <= 0) {
            throw new ApplicationException(ErrorType.User.AMOUNT_MUST_BE_POSITIVE);
        }

        this.amount += amountToCharge;
    }
}
