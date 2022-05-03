package com.example.service.stats;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class Stats {
    protected Long totalRequests = 0L;
    protected Long wrongRequests = 0L;

    @Setter
    protected Integer min = 0;

    @Setter
    protected Integer max = 0;

    @Setter
    protected Integer mostCommon = 0;

    public void increaseTotalRequests() {
        totalRequests++;
    }

    public void increaseWrongRequests() {
        wrongRequests++;
    }
}