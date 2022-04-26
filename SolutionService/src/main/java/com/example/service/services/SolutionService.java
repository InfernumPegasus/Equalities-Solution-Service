package com.example.service.services;

import com.example.service.db.ResultDAO;
import com.example.service.entity.ResultsEntity;
import com.example.service.process.InputParams;
import com.example.service.logger.MyLogger;
import com.example.service.stats.Stats;
import lombok.Getter;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolutionService {

    @Autowired
    public void setCache(CacheService cache) {
        this.cache = cache;
    }

    @Autowired
    public void setStats(Stats stats) {
        this.stats = stats;
    }

    @Autowired
    public void setResultDAO(ResultDAO resultDAO) {
        this.resultDAO = resultDAO;
    }

    @Getter
    private ResultDAO resultDAO;

    private CacheService cache;

    private Stats stats;

    public static boolean isCorrectParams(InputParams params) {
        if (
                params == null || params.firstValue() == null || params.secondValue() == null
        ) {
            MyLogger.log(Level.ERROR, "Wrong params: " + params);

            return false;
        }
        return true;
    }

    public Integer calculateRoot(InputParams inputParams) {
        Integer root;
        // Increasing requests counter
        stats.increaseTotalRequests();

        // Trying to find root in cache
        var found = cache.find(inputParams);

        if (found != null) {
            root = found;
            MyLogger.log(Level.WARN, "Value " + root + " found in cache!");
        } else {
            // If not found
            root = inputParams.secondValue() - inputParams.firstValue();

            if (root < inputParams.leftBorder() || root > inputParams.rightBorder()) {
                // Increasing wrong requests counter
                stats.increaseWrongRequests();

                var message = """
                        Root %d is not in range from [%d; %d]
                        """.formatted(root, inputParams.leftBorder(), inputParams.rightBorder());

                MyLogger.log(Level.ERROR, message);
                return null;
            }

            // Adding { inputParams, root } to cache
            cache.add(inputParams, root);

            var res = new ResultsEntity();
            res.setId(0);
            res.setRoot(root);
            resultDAO.save(res);
        }
        return root;
    }
}