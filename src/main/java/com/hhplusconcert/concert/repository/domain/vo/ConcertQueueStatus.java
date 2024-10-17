package com.hhplusconcert.concert.repository.domain.vo;

public enum ConcertQueueStatus {
    WAITING,
    PROGRESS,
    //    DONE,
    EXPIRED;

    public String getCode() {
        return name();
    }
}
