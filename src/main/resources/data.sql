-- concert
INSERT INTO concert (id, title)
VALUES (1, 'Classic Concert');
INSERT INTO concert (id, title)
VALUES (2, 'Rock Concert');

-- concert_schedule
INSERT INTO concert_schedule (id, concert_id, open_date, start_at, end_at, status)
VALUES (1, 1, '2024-11-01', '2024-11-01 18:00:00', '2024-11-01 21:00:00', 'AVAILABLE');
INSERT INTO concert_schedule (id, concert_id, open_date, start_at, end_at, status)
VALUES (2, 2, '2024-11-02', '2024-11-02 19:00:00', '2024-11-02 22:00:00', 'AVAILABLE');

-- concert_queue
-- INSERT INTO concert_queue (id, concert_schedule_id, entered_at, expired_at, user_id, token, status)
-- VALUES (1, 1, '2024-11-01 17:00:00', '2024-11-01 20:30:00', 101, 'token123', 'WAITING');
-- INSERT INTO concert_queue (id, concert_schedule_id, entered_at, expired_at, user_id, token, status)
-- VALUES (2, 2, '2024-11-02 18:30:00', '2024-11-02 21:30:00', 102, 'token456', 'PROGRESS');

-- concert_seat
INSERT INTO concert_seat (id, concert_schedule_id, position, amount, is_reserved)
VALUES (1, 1, 101, 50000, 0);
INSERT INTO concert_seat (id, concert_schedule_id, position, amount, is_reserved)
VALUES (2, 1, 102, 50000, 1);
INSERT INTO concert_seat (id, concert_schedule_id, position, amount, is_reserved)
VALUES (3, 2, 201, 70000, 0);

-- payment
-- INSERT INTO payment (id, user_id, price, created_at, status)
-- VALUES (1, 101, 50000, '2024-10-30 14:00:00', 'DONE');
-- INSERT INTO payment (id, user_id, price, created_at, status)
-- VALUES (2, 102, 70000, '2024-10-31 15:00:00', 'PROGRESS');

-- reservation
-- INSERT INTO reservation (id, concert_schedule_id, user_id, seat_id, payment_id, reserved_at, status)
-- VALUES (1, 1, 101, 1, 1, '2024-10-30 14:05:00', 'PAID');
-- INSERT INTO reservation (id, concert_schedule_id, user_id, seat_id, payment_id, reserved_at, status)
-- VALUES (2, 2, 102, 3, 2, '2024-10-31 15:10:00', 'RESERVED');

-- user
INSERT INTO user (id, user_id, amount)
VALUES (1, 'user123', 100000);
INSERT INTO user (id, user_id, amount)
VALUES (2, 'user456', 50000);
