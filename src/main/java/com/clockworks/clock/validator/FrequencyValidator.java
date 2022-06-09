package com.clockworks.clock.validator;


import com.clockworks.clock.exception.FrequencyExceedsLimitException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FrequencyValidator {

    private static final int LOWER_LIMIT = 5;
    private static final int UPPER_LIMIT = 14400;

    public boolean isValid(int frequency) {
        if(!isFrequencyWithinLimits(frequency)) {
            log.error("Frequency: {} exceeds lower: {} and upper: {}", frequency, LOWER_LIMIT, UPPER_LIMIT);
            throw new FrequencyExceedsLimitException("Frequency must be between 5 seconds and 14400 seconds (4 hours)");
        }
        return true;
    }

    private boolean isFrequencyWithinLimits(int frequency) {
        return frequency > LOWER_LIMIT && frequency < UPPER_LIMIT;
    }
}
