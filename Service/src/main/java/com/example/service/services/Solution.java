package com.example.service.services;

import com.example.service.process.InputParams;
import com.example.service.responses.Response;
import com.example.service.stats.ServiceStats;
import com.example.service.logger.MyLogger;
import lombok.Getter;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Solution {

    @Getter
    public Counter counter;

    private Cache cache;

    private @Nullable Integer root;

    public static boolean isCorrectParams(@NotNull InputParams params) {
        return params.getFirstValue() != null &&
                params.getSecondValue() != null;
    }

    public void calculateRoot(@NotNull InputParams inputParams) {
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

    @Autowired
    public void setCache(Cache cache) {
        this.cache = cache;
    }

    @Autowired
    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    public Response getResponse() {
        return new Response(root);
    }

    @Contract(pure = true)
    public Integer getRoot() {
        assert root != null;
        return root;
    }

    public Long getCount() {
        return counter.getCount();
    }
}