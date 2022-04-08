package com.example.service.process;

import com.example.service.logger.MyLogger;
import lombok.Getter;
import org.apache.logging.log4j.Level;

public class Counter {

    @Getter private long count;

    public synchronized void increase() {
        count++;
        MyLogger.log(Level.INFO, "Solution Service requests amount is: " + count);
    }
}
