package com.hhplusconcert.concert.repository.domain.vo;

public enum ConcertScheduleStatus {
    SOLD_OUT, AVAILABLE;

    public String getCode() {
        return name();
    }
}
