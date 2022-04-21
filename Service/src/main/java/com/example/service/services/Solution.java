package com.example.service.services;

import com.example.service.process.InputParams;
import com.example.service.stats.ServiceStats;
import com.example.service.logger.MyLogger;
import lombok.Getter;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Solution {

    @Getter
    public Counter counter;

    private Cache cache;

    public static boolean isCorrectParams(InputParams params) {
        return params != null &&
                params.getFirstValue() != null &&
                params.getSecondValue() != null;
    }

    public Integer calculateRoot(InputParams inputParams) {
        Integer root;
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
            root = inputParams.getSecondValue() - inputParams.getFirstValue();

            if (root < inputParams.getLeftBorder() || root > inputParams.getRightBorder()) {
                // Increasing wrong requests counter
                ServiceStats.increaseWrongRequests();

                var message = """
                        Root %d is not in range from [%d;%d]
                        """.formatted(root, inputParams.getLeftBorder(), inputParams.getRightBorder());

                MyLogger.log(Level.ERROR, message);
                return null;
            }

            // Adding { inputParams, root } to cache
            cache.add(inputParams, root);
        }
        return root;
    }

    @Autowired
    public void setCache(Cache cache) {
        this.cache = cache;
    }

    @Autowired
    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    public Long getCount() {
        return counter.getCount();
    }
}