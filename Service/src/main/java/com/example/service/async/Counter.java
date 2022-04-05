package com.example.service.async;

import com.example.service.logger.MyLogger;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Contract;

public class Counter {

    private long count;

    @Contract(pure = true)
    public long getCount() {
        return count;
    }

    public synchronized void increase() {
        count++;
        MyLogger.log(Level.INFO, "Solution Service requests amount is: " + count);
    }
}
