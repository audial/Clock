package com.clockworks.clock.config;

import com.clockworks.clock.cache.Cache;
import com.clockworks.clock.client.HttpClient;
import com.clockworks.clock.service.ApplicationTimer;
import com.clockworks.clock.service.SubscriptionService;
import com.clockworks.clock.validator.FrequencyValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Timer;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public Cache cache(HttpClient httpClient) {
        Cache cache = new Cache(httpClient);
        cache.clearElements();
        return cache;
    }

    @Bean
    public SubscriptionService subscriptionService(Cache cache, FrequencyValidator frequencyValidator) {
        return new SubscriptionService(cache, frequencyValidator);
    }

    @Bean
    public HttpClient requestBinClient() {
        return new HttpClient();
    }

    @Bean
    public ApplicationTimer applicationTimer(Timer timer, Cache cache) {
        return new ApplicationTimer(1, timer, cache);
    }

    @Bean
    public Timer timer() {
        return new Timer();
    }

    @Bean
    public FrequencyValidator frequencyValidator() {
        return new FrequencyValidator();
    }
}
