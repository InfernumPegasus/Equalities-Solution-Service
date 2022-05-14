package com.example.service.services;

import com.example.service.cache.CacheService;
import com.example.service.dao.PostgresQLDaoImpl;
import com.example.service.entity.ResultsEntity;
import com.example.service.input.InputParams;
import com.example.service.logger.MyLogger;
import com.example.service.stats.StatsProvider;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Service which is used for solving
 * equalities from provided params.
 */
@Service
public record SolutionService(PostgresQLDaoImpl postgresQLDao, CacheService cache, StatsProvider statsProvider) {

    @Autowired
    public SolutionService {
    }

    /**
     * Method which is used to check
     * if {@link InputParams} value is valid
     * @param params value to be checked
     * @return true if correct, false otherwise
     */
    public boolean isCorrectParams(InputParams params) {
        if (params == null ||
                params.firstValue() == null ||
                params.secondValue() == null) {
            MyLogger.log(Level.ERROR, "Wrong params: " + params);
            statsProvider.increaseWrongRequests();

            return false;
        }
        return true;
    }

    /**
     * Solves equality by provided
     * {@link InputParams}.
     * Adds calculated value to cache,
     * DataBase and makes it available
     * for statistics collected by {@link StatsProvider}.
     * @param inputParams value for solving
     * @return {@link Integer} calculated root
     */
    private Integer solve(InputParams inputParams) {
        // Increasing requests counter
        statsProvider.increaseTotalRequests();

        Function<InputParams, Integer> solve = e -> {
            if (cache.contains(e)) {
                return cache.get(e);
            }

            int root = e.secondValue() - e.firstValue();
            if (root >= e.leftBorder() && root <= e.rightBorder()) {
                return root;
            }
            throw new IllegalArgumentException("Wrong params!");
        };
        var res = solve.apply(inputParams);
        cache.add(inputParams, res);
        statsProvider.addRoot(res);

        var entity = new ResultsEntity();
        entity.setId(0);
        entity.setRoot(res);
        postgresQLDao.save(entity);

        return res;
    }

    /**
     * Method which is used for
     * solving single equality.
     * @param inputParams value for solving
     * @return {@link Integer} calculated root
     */
    public Integer calculate(InputParams inputParams) {
        return solve(inputParams);
    }

    /**
     * Method which is used for
     * solving multiple equalities.
     * @param inputParams values for solving
     * @return {@link Collection} of calculated roots
     */
    public Collection<Integer> calculate(@NotNull Collection<InputParams> inputParams) {
        return inputParams
                .stream()
                .filter(this::isCorrectParams)
                .map(this::solve)
                .collect(Collectors.toList());
    }
}