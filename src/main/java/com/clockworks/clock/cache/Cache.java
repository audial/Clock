package com.clockworks.clock.cache;


import com.clockworks.clock.client.HttpClient;
import com.clockworks.clock.model.Subscription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class Cache {

    private final HttpClient client;

    public Cache(HttpClient client) {
        this.client = client;
    }

    private static final long EXPIRED_TIME_IN_SEC = 5L;

    private static final ConcurrentHashMap<String, Subscription> cache = new ConcurrentHashMap<>();

    public void add(String key, int frequency) {
        if (key == null) {
            return;
        }
        cache.put(key, new Subscription(expiryDate(frequency), frequency));
    }

    public void remove(String key) {
        cache.remove(key);
    }

    public Optional<Subscription> get(String key) {
        return Optional.ofNullable(cache.get(key));
    }

    public void clearElements() {
        Date currentTime = new Date();
        Date actualExpiredTime = new Date();

        actualExpiredTime.setTime(currentTime.getTime() - EXPIRED_TIME_IN_SEC * 1000L);
        for (Map.Entry<String, Subscription> entry : cache.entrySet()) {
            String key = entry.getKey();
            Subscription value = entry.getValue();
            Date date = value.getDate();

            while (date.compareTo(actualExpiredTime) < 0) {
                log.info("Sending time: {} to url: {}", value.getDate(), key);
                client.sendTime(key, date.toString());
                cache.remove(key);
                int frequency = value.getFrequency();
                cache.put(key, new Subscription(expiryDate(frequency), frequency));
                break;
            }
        }
    }

    private Date expiryDate(int frequency) {
        Date now = new Date();
        long time = now.getTime();
        return new Date(time + (frequency * 1000L));
    }
}