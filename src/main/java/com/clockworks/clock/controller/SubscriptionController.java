package com.clockworks.clock.controller;

import com.clockworks.clock.model.RegisterResponse;
import com.clockworks.clock.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RegisterResponse> register(
            @RequestParam(name = "url") String url,
            @RequestParam(name = "frequency") int frequency) {
        log.info("Called POST /register with url={}, frequency={}", url, frequency);
        return new ResponseEntity<>(subscriptionService.register(url, frequency), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/deregister", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deregister(
            @RequestParam(name = "url") String url) {
        log.info("Called DELETE /deregister with url={}", url);
        subscriptionService.deregister(url);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/frequency", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> adjustFrequency(
            @RequestParam(name = "url") String url,
            @RequestParam(name = "frequency") int frequency) {
        log.info("Called PUT /frequency with url={}, frequency={}", url, frequency);
        subscriptionService.adjustFrequency(url, frequency);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


