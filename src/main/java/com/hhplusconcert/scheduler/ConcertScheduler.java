package com.hhplusconcert.scheduler;

import com.hhplusconcert.concert.repository.ConcertQueueRepository;
import com.hhplusconcert.concert.repository.ConcertScheduleRepository;
import com.hhplusconcert.concert.repository.ReservationRepository;
import com.hhplusconcert.concert.repository.domain.ConcertQueue;
import com.hhplusconcert.concert.repository.domain.ConcertSchedule;
import com.hhplusconcert.concert.repository.domain.Reservation;
import com.hhplusconcert.concert.repository.domain.vo.ConcertQueueStatus;
import com.hhplusconcert.concert.repository.domain.vo.ConcertScheduleStatus;
import com.hhplusconcert.concert.repository.domain.vo.ReservationStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConcertScheduler {

    private final ConcertQueueRepository concertQueueRepository;
    private final ConcertScheduleRepository concertScheduleRepository;
    private final ReservationRepository reservationRepository;

    private final int SCHEDULING_INTERVAL = 5000; // 5 초
    private final int PASS_THROUGH_SIZE = 3; // 1 명

    /**
     * 스케줄에 따라 ConcertQueue의 상태를 업데이트
     */
    @Scheduled(fixedRate = SCHEDULING_INTERVAL)
    public void updateConcertQueueStatuses() {
        log.info("===== Scheduling Start =====");
        updateWaitingToProgressStatus();
        updateProgressToExpiredStatus();
        expireReservationWithTransaction();
        log.info("===== Scheduling End =====");
    }

    @Transactional
    void expireReservationWithTransaction() {
        List<Reservation> byStatusAndReservedAtBefore = reservationRepository.findByStatusAndReservedAtBefore(ReservationStatus.RESERVED, LocalDateTime.now().minusMinutes(30));
        byStatusAndReservedAtBefore.forEach(reservation -> {
            reservation.expire();
            reservationRepository.save(reservation);
        });
    }

    /**
     * 상태가 WAITING인 ConcertQueue를 PASS_THROUGH_SIZE만큼 PROGRESS 상태로 업데이트
     */
    private void updateWaitingToProgressStatus() {
        List<ConcertSchedule> availableSchedules = concertScheduleRepository.findAllByStatus(ConcertScheduleStatus.AVAILABLE);

        availableSchedules.forEach(schedule -> {
            List<ConcertQueue> concertQueuesToProgress = concertQueueRepository
                    .findByConcertScheduleIdAndStatusOrderByEnteredAtAsc(schedule.getId(), ConcertQueueStatus.WAITING, PASS_THROUGH_SIZE, 0)
                    .stream()
                    .peek(queue -> {
                        queue.toProgress();
                    })
                    .collect(Collectors.toList());

            log.info("Concert Schedule {}, Waiting -> Progress: {}", schedule.getId(),
                    concertQueuesToProgress.stream().map(ConcertQueue::getId).collect(Collectors.toList()));
            concertQueueRepository.updateAll(concertQueuesToProgress);
        });
    }

    /**
     * 상태가 PROGRESS이고 만료 시간이 지난 ConcertQueue를 EXPIRED 상태로 업데이트
     */
    private void updateProgressToExpiredStatus() {
        List<ConcertQueue> expiredQueues = concertQueueRepository
                .findByStatusAndExpiredAtBefore(ConcertQueueStatus.PROGRESS, LocalDateTime.now())
                .stream()
                .peek(queue -> queue.expire())
                .collect(Collectors.toList());

        log.info("Progress -> Expired: {}", expiredQueues.stream().map(ConcertQueue::getId).collect(Collectors.toList()));
        concertQueueRepository.updateAll(expiredQueues);
    }
}
