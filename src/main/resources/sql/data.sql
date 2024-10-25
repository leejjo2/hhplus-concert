-- concert 테이블 예시 데이터
INSERT INTO concert (id, title)
VALUES (1, 'Classic Night Concert'),
       (2, 'Rock Festival'),
       (3, 'Jazz Evening');

-- concert_schedule 테이블 예시 데이터
INSERT INTO concert_schedule (id, concert_id, open_date, start_at, end_at, status)
VALUES (1, 1, '2024-11-15', '2024-11-15 18:00:00', '2024-11-15 20:00:00', 'AVAILABLE'),
       (2, 2, '2024-11-16', '2024-11-16 17:00:00', '2024-11-16 19:00:00', 'SOLD_OUT'),
       (3, 3, '2024-11-17', '2024-11-17 19:00:00', '2024-11-17 21:00:00', 'AVAILABLE');

-- -- concert_queue 테이블 예시 데이터
-- INSERT INTO concert_queue (id, concert_schedule_id, user_id, token, status, entered_at, expired_at) VALUES
--                                                                                                         (1, 1, 101, 'token1', 'WAITING', '2024-11-15 10:00:00', '2024-11-15 10:30:00'),
--                                                                                                         (2, 2, 102, 'token2', 'PROGRESS', '2024-11-16 12:00:00', '2024-11-16 12:30:00'),
--                                                                                                         (3, 3, 103, 'token3', 'EXPIRED', '2024-11-17 09:00:00', '2024-11-17 09:30:00');

-- concert_seat 테이블 예시 데이터
INSERT INTO concert_seat (id, concert_schedule_id, amount, is_reserved, position, version)
VALUES (1, 1, 5000, 0, 101, 1),
       (2, 2, 6000, 1, 102, 1),
       (3, 3, 5500, 0, 103, 1);

-- -- payment 테이블 예시 데이터
-- INSERT INTO payment (id, user_id, price, created_at, status) VALUES
--                                                                  (1, 101, 5000, '2024-11-15 10:15:00', 'DONE'),
--                                                                  (2, 102, 6000, '2024-11-16 12:15:00', 'CANCELED'),
--                                                                  (3, 103, 5500, '2024-11-17 09:15:00', 'PROGRESS');

-- -- reservation 테이블 예시 데이터
-- INSERT INTO reservation (id, concert_schedule_id, payment_id, reserved_at, seat_id, user_id, status) VALUES
--                                                                                                          (1, 1, 1, '2024-11-15 10:20:00', 1, 101, 'PAID'),
--                                                                                                          (2, 2, 2, '2024-11-16 12:20:00', 2, 102, 'CANCELED'),
--                                                                                                          (3, 3, 3, '2024-11-17 09:20:00', 3, 103, 'RESERVED');

-- user 테이블 예시 데이터
INSERT INTO user (id, amount, user_id)
VALUES (1, 10000, 'user101'),
       (2, 15000, 'user102'),
       (3, 12000, 'user103');
