package com.hhplusconcert.concert.repository.domain.vo;

public enum PaymentStatus {
    PROGRESS, DONE, CANCELED;

    public String getCode() {
        return name();
    }
}
