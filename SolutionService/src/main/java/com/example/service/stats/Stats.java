package com.example.service.stats;

import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * Class which contains
 * all calculated stats.
 */
@Component
@Getter
public class Stats {
    protected Long totalRequests = 0L;
    protected Long wrongRequests = 0L;

    protected Integer min = 0;

    protected Integer max = 0;

    protected Integer mostCommon = 0;
}