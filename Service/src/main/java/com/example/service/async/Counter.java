package com.example.service.async;

import com.example.service.logger.MyLogger;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Service;

@Service
public class Counter {

    private long count;

    public long getCount() {
        return count;
    }

    public void increase() {
        count++;
        MyLogger.log(Level.INFO, "Current count amount is: " + count);
    }
}
