package com.example.service.async;

import com.example.service.logger.MyLogger;
import org.apache.logging.log4j.Level;

public class Counter {

    private long count;

    public long getCount() {
        return count;
    }

    public synchronized void increase() {
        count++;
        MyLogger.log(Level.INFO, "Current count amount is: " + count);
    }
}
