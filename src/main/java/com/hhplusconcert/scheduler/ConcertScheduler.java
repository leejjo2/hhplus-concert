package com.hhplusconcert.scheduler;

import com.hhplusconcert.concert.repository.ConcertQueueRepository;
import com.hhplusconcert.concert.repository.ConcertScheduleRepository;
import com.hhplusconcert.concert.repository.domain.ConcertQueue;
import com.hhplusconcert.concert.repository.domain.ConcertSchedule;
import com.hhplusconcert.concert.repository.domain.vo.ConcertQueueStatus;
import com.hhplusconcert.concert.repository.domain.vo.ConcertScheduleStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConcertScheduler {

    private final ConcertQueueRepository concertQueueRepository;
    private final ConcertScheduleRepository concertScheduleRepository;
    private final int SCHEDULING_INTERVAL = 5000; // 5 초
    private final int PASS_THROUGH_SIZE = 3; // 1 명

    private void updateConcertQueueStatuses() {

        List<ConcertSchedule> concertSchedules = concertScheduleRepository.findAllByStatus(ConcertScheduleStatus.AVAILABLE);
        for (ConcertSchedule concertSchedule : concertSchedules) {

            Long concertScheduleId = concertSchedule.getId();
            // Waiting -> Progress
            List<ConcertQueue> concertQueues = concertQueueRepository.findByConcertScheduleIdAndStatusOrderByEnteredAtAsc(
                    concertScheduleId,
                    ConcertQueueStatus.WAITING,
                    PASS_THROUGH_SIZE,
                    0
            ).stream().map(concertQueue -> {
                concertQueue.setStatus(ConcertQueueStatus.PROGRESS);
                concertQueue.setExpiredAt(LocalDateTime.now().plusMinutes(30));
                return concertQueue;
            }).collect(Collectors.toList());

            log.info("Concert Schedule {}, Waiting -> Progress = {}", concertSchedule.getId(), concertQueues.stream()
                    .map(ConcertQueue::getId)
                    .toList());
            concertQueueRepository.updateAll(concertQueues);
        }

        // Progress -> Expired
        List<ConcertQueue> concertQueues = concertQueueRepository.findByStatusAndExpiredAtBefore(ConcertQueueStatus.PROGRESS, LocalDateTime.now()).stream().map(concertQueue -> {
            concertQueue.setStatus(ConcertQueueStatus.EXPIRED);
            return concertQueue;
        }).collect(Collectors.toList());

        log.info("Progress -> Expired = {}", concertQueues.stream()
                .map(ConcertQueue::getId)
                .toList());
        concertQueueRepository.updateAll(concertQueues);
    }

    @Scheduled(fixedRate = SCHEDULING_INTERVAL)
    public void updateWaitingStatus() {
        log.info("===== Scheduling Start=====");
        updateConcertQueueStatuses();
        log.info("===== Scheduling End=====");
    }
}
