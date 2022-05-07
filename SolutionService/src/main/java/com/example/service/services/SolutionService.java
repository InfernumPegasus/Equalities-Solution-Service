package com.example.service.services;

import com.example.service.dao.PostgresQLDao;
import com.example.service.entity.ResultsEntity;
import com.example.service.input.InputParams;
import com.example.service.logger.MyLogger;
import com.example.service.stats.StatsProvider;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SolutionService {

    @Autowired
    public void setCache(CacheService cache) {
        this.cache = cache;
    }

    @Autowired
    @Qualifier("postgresData")
    public void setPostgresQLDao(PostgresQLDao postgresQLDao) {
        this.postgresQLDao = postgresQLDao;
    }

    @Autowired
    public void setStatsProvider(StatsProvider statsProvider) {
        this.statsProvider = statsProvider;
    }

    private PostgresQLDao postgresQLDao;
    private CacheService cache;
    private StatsProvider statsProvider;

    public boolean isCorrectParams(InputParams params) {
        if (params == null ||
                params.firstValue() == null ||
                params.secondValue() == null
        ) {
            MyLogger.log(Level.ERROR, "Wrong params: " + params);
            statsProvider.increaseWrongRequests();

            return false;
        }
        return true;
    }

    private @Nullable Integer calculateRoot(InputParams inputParams) {
        Integer root;
        // Increasing requests counter
        statsProvider.increaseTotalRequests();

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
                statsProvider.increaseWrongRequests();

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
            postgresQLDao.save(res);
        }
        statsProvider.addRoot(root);
        return root;
    }

    public Integer calculate(InputParams inputParams) {
        if (!isCorrectParams(inputParams)) {
            throw new IllegalArgumentException("Wrong params: " + inputParams);
        }
        return calculateRoot(inputParams);
    }

    public Collection<Integer> calculate(@NotNull Collection<InputParams> inputParams) {
        return inputParams
                .stream()
                .filter(this::isCorrectParams)
                .map(this::calculateRoot)
                .filter(Objects::nonNull)
                .peek(statsProvider::addRoot)
                .collect(Collectors.toList());
    }
}