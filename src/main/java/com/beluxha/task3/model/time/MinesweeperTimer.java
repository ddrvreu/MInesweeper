package com.beluxha.task3.model.time;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Slf4j
public class MinesweeperTimer {
    private static final long TIMER_DELAY = 1000;
    private final List<MinesweeperTimerListener> listeners;
    private final Timer timer = new Timer();
    @Getter
    private long timerOperationTime;
    private long startTime;
    private long currentSeconds;

    public MinesweeperTimer(List<MinesweeperTimerListener> listeners) {
        this.listeners = listeners;
    }

    public void start() {
        log.info("Starting timer");
        startTimer();
    }

    public void stopTimer() {
        log.info("Stopping timer");
        timerOperationTime = System.currentTimeMillis() - startTime;
        timer.cancel();
    }

    private void startTimer() {
        startTime = System.currentTimeMillis();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                currentSeconds++;
                listeners.forEach(listener -> listener.updateTime(currentSeconds));
            }
        }, TIMER_DELAY, TIMER_DELAY);
    }
}
