package com.clockworks.clock.service;

import com.clockworks.clock.cache.Cache;
import com.clockworks.clock.exception.DuplicateRecordFoundException;
import com.clockworks.clock.exception.UrlNotFoundException;
import com.clockworks.clock.model.RegisterResponse;
import com.clockworks.clock.validator.FrequencyValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SubscriptionService {

    private final Cache cache;
    private final FrequencyValidator validator;

    public SubscriptionService(Cache cache, FrequencyValidator validator) {
        this.cache = cache;
        this.validator = validator;
    }

    public RegisterResponse register(String url, int frequency) {
        validator.isValid(frequency);
        if (cache.get(url).isPresent()) {
            log.error("Register failed. Url: {} already exists", url);
            throw new DuplicateRecordFoundException(String.format("Register failed. Url: %s already exists", url));
        }
        cache.add(url, frequency);
        return new RegisterResponse(url, frequency);
    }

    public void deregister(String url) {
        if (cache.get(url).isEmpty()) {
            log.error("Deregister failed. Url: {} not found", url);
            throw new UrlNotFoundException(String.format("Deregister failed. Url: %s not found", url));
        }
        cache.remove(url);
    }


    public void adjustFrequency(String url, int frequency) {
        validator.isValid(frequency);
        if (cache.get(url).isEmpty()) {
            log.error("Adjust frequency failed. Url: {} not found", url);
            throw new UrlNotFoundException(String.format("Adjust Frequency failed. Url: %s not found", url));
        }
        cache.add(url, frequency);
    }
}
