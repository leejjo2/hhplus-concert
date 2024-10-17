package com.hhplusconcert.concert.repository.domain.vo;

public enum ConcertQueueStatus {
    WATING,
    PROGRESS,
    //    DONE,
    EXPIRED;

    public String getCode() {
        return name();
    }
}
