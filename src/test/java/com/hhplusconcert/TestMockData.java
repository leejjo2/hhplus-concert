package com.hhplusconcert;

import java.time.LocalDate;

public class TestMockData {
    public static class User {
        public static final Long userId_123 = 123L;
    }

    public static class Concert {
        public static final Long concertId_1 = 1L;

        public static class ConcertSchedule {

            public static final Long concertScheduleId_1 = 1L;
            public static final Long concertScheduleId_2 = 2L;
            public static final Long concertScheduleId_3 = 3L;

            // LocalDate
            public static final LocalDate openDate_241001 = LocalDate.of(2024, 10, 1);
            public static final LocalDate openDate_241002 = LocalDate.of(2024, 10, 2);
            public static final LocalDate openDate_241003 = LocalDate.of(2024, 10, 3);
        }
    }
}
