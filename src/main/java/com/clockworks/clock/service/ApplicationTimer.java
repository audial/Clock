package com.clockworks.clock.service;

import com.clockworks.clock.cache.Cache;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;

@Component
public class ApplicationTimer {

    private final Cache cache;

    public ApplicationTimer(int seconds, Timer timer, Cache cache) {
        this.cache = cache;
        timer.schedule(new Reminder(), 0, seconds * 1000L);
    }

    private class Reminder extends TimerTask {
        public void run() {
            cache.clearElements();
        }
    }
}
