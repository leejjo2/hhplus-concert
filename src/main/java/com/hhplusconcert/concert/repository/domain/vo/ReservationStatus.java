package com.hhplusconcert.concert.repository.domain.vo;

public enum ReservationStatus {
    TEMP_RESERVED, RESERVED, PAID, CANCELED;

    public String getCode() {
        return name();
    }
}
