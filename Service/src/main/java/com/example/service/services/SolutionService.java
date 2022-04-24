package com.example.service.services;

import com.example.service.process.InputParams;
import com.example.service.logger.MyLogger;
import com.example.service.stats.Stats;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolutionService {

    @Autowired
    public void setCache(CacheService cache) {
        this.cache = cache;
    }

    private CacheService cache;

    public static boolean isCorrectParams(InputParams params) {
        return params != null &&
                params.getFirstValue() != null &&
                params.getSecondValue() != null;
    }

    public Integer calculateRoot(InputParams inputParams) {
        Integer root;
        // Increasing requests counter
        Stats.increaseTotalRequests();

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
                Stats.increaseWrongRequests();

                var message = """
                        Root %d is not in range from [%d; %d]
                        """.formatted(root, inputParams.getLeftBorder(), inputParams.getRightBorder());

                MyLogger.log(Level.ERROR, message);
                return null;
            }

            // Adding { inputParams, root } to cache
            cache.add(inputParams, root);
        }
        return root;
    }
}