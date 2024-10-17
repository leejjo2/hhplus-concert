-- Concert 테이블에 데이터 삽입
INSERT INTO concert (title) VALUES ('Concert A');
INSERT INTO concert (title) VALUES ('Concert B');

-- ConcertSchedule 테이블에 데이터 삽입
INSERT INTO concert_schedule (concert_id, open_date, start_at, end_at, status)
VALUES
    (1, '2024-10-20', '2024-10-20 19:00:00', '2024-10-20 21:00:00', 'AVAILABLE'),
    (2, '2024-10-21', '2024-10-21 18:00:00', '2024-10-21 20:00:00', 'AVAILABLE');

-- ConcertSeat 테이블에 데이터 삽입
INSERT INTO concert_seat (concert_schedule_id, position, amount) VALUES
                                                                     (1, 1, 50),
                                                                     (1, 2, 50),
                                                                     (2, 1, 75),
                                                                     (2, 2, 75);

-- User 테이블에 데이터 삽입
INSERT INTO user (user_id, amount) VALUES
                                       ('user1', 100),
                                       ('user2', 200);

