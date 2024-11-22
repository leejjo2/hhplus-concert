package com.hhplusconcert.concert.repository.domain;

import com.hhplusconcert.shared.error.ApplicationException;
import com.hhplusconcert.shared.error.ErrorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConcertSeatTest {

    private ConcertSeat concertSeat;

    @BeforeEach
    void setUp() {
        // 각 테스트가 실행되기 전에 새로운 ConcertSeat 인스턴스를 초기화합니다.
        concertSeat = new ConcertSeat(1L, 1L, 100, 1, false);
    }

    @Test
    @DisplayName("좌석이 예약되지 않은 상태에서 reserve 호출 시 예약 성공")
    void reserve_UnreservedSeat_SuccessfullyReserved() {
        // 예약 호출
        concertSeat.reserve();

        // 상태가 예약됨으로 변경되었는지 확인
        assertThat(concertSeat.getIsReserved()).isTrue();
    }

    @Test
    @DisplayName("이미 예약된 좌석에서 reserve 호출 시 예외 발생")
    void reserve_AlreadyReservedSeat_ThrowsException() {
        // 이미 예약된 상태로 변경
        concertSeat.setIsReserved(true);

        // 예외 발생 검증
        assertThatThrownBy(concertSeat::reserve)
                .isInstanceOf(ApplicationException.class)
                .hasMessage(ErrorType.Concert.CONCERT_SEAT_ALREADY_RESERVED.getMessage());
    }
}
