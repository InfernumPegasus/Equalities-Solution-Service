package com.example.service.logger;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class that provides logging ability
 */
public class MyLogger {

    private static final Logger logger = LogManager.getLogger(MyLogger.class);

    /**
     * Logs {@link Object} in certain {@link Level}
     * @param level log level
     * @param message log message
     */
    public static void log(Level level, Object message) {
        logger.log(level, message);
    }

}