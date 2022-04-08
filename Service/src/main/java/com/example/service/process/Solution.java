package com.example.service.process;

import com.example.service.cache.Cache;
import com.example.service.stats.ServiceStats;
import com.example.service.logger.MyLogger;
import lombok.Getter;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.example.service.controllers.MainController.context;

public class Solution {

    @Getter
    public static final Counter counter =
            context.getBean("counter", Counter.class);

    private static final Cache cache =
            context.getBean("cache", Cache.class);

    private static @Nullable Integer root;

    public static void calculateRoot(@NotNull InputParams inputParams) {
        // Increasing requests counter
        counter.increase();
        ServiceStats.increaseTotalRequests();

        // Trying to find root in cache
        var found = cache.find(inputParams);

        if (found != null) {
            root = found;
            MyLogger.log(Level.WARN, "Value " + root + " found in cache!");
        } else {
            // If not found
            found = inputParams.getSecondValue() - inputParams.getFirstValue();

            if (found < inputParams.getLeftBorder() || found > inputParams.getRightBorder()) {
                // Increasing wrong requests counter
                ServiceStats.increaseWrongRequests();

                throw new ArithmeticException(
                        "Root " + found + " is not in range from " + inputParams.getLeftBorder() +
                                " and " + inputParams.getRightBorder());
            }

            // Adding { inputParams, root } to cache
            root = found;
            cache.add(inputParams, root);
        }
    }

    @Contract(pure = true)
    public static @NotNull Integer getRoot() {
        assert root != null;
        return root;
    }
}