package com.example.service.services;

import com.example.service.cache.CacheService;
import com.example.service.dao.PostgresQLDaoImpl;
import com.example.service.input.InputParams;
import com.example.service.logger.MyLogger;
import com.example.service.stats.StatsProvider;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;


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
     * Solves equality by provided
     * {@link InputParams}.
     * Adds calculated value to cache,
     * DataBase and makes it available
     * for statistics collected by {@link StatsProvider}.
     *
     * @param params value for solving
     * @return {@link Integer} calculated root
     */
    private Integer calculateRoot(InputParams params) {
        // Increasing requests counter
        statsProvider.increaseTotalRequests();

        int res = cache.contains(params) ? cache.get(params) :
                params.getSecondValue() - params.getFirstValue();

        boolean correct = res >= params.getLeftBorder() && res <= params.getRightBorder();

        if (!correct) {
            throw new IllegalArgumentException("Out of borders: " + params);
        }

        return res;
    }

    /**
     * Method which is used for
     * solving single equality
     *
     * @param params params for solving
     * @return {@link Integer} root
     */
    public Integer solve(InputParams params) {
        return calculateRoot(params);
    }

    /**
     * Method which is used for
     * solving multiple equalities.
     * Handles exception if they occur
     * while calculation
     *
     * @param params params list for solving
     * @return {@link Collection} of calculated roots
     */
    public Collection<Integer> solve(@NotNull Collection<InputParams> params) {

        return params.stream().map(item ->
                        CompletableFuture.supplyAsync(calculateRootWrapper(item)))
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .peek(root -> {
                    // Saving to db
                    postgresQLDao.save(root);
                    // Adding to statistics
                    statsProvider.add(root);
                }).toList();
    }

    /**
     * calculateRoot method wrapper which is
     * used for exception handling while solving
     *
     * @param inputParams input params for solving
     * @return result supplier
     */
    @NotNull
    private Supplier<Integer> calculateRootWrapper(InputParams inputParams) {
        return () -> {
            try {
                return calculateRoot(inputParams);
            } catch (IllegalArgumentException e) {
                statsProvider.increaseWrongRequests();
                MyLogger.error(e.getMessage());
            }
            return null;
        };
    }
}