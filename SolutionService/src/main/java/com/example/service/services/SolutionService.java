package com.example.service.services;

import com.example.service.cache.CacheService;
import com.example.service.dao.PostgresQLDaoImpl;
import com.example.service.input.InputParams;
import com.example.service.logger.MyLogger;
import com.example.service.stats.StatsProvider;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Function;

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
            statsProvider.increaseWrongRequests();

            return false;
        }
        return true;
    }

    /**
     * Checks if root is between left and right borders
     * @param root value to be checked
     * @param left left border
     * @param right right border
     * @return true if correct, else otherwise
     */
    private boolean isRootBetweenBorders(int root, int left, int right) {
        return root >= left && root <= right;
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

        Function<InputParams, Integer> calcFunction =
                i -> cache.contains(i) ? cache.get(i) :
                        i.secondValue() - i.firstValue();

        return calcFunction
                .andThen(root -> {
                    boolean correct = isRootBetweenBorders(root,
                            inputParams.leftBorder(),
                            inputParams.rightBorder());

                    if (!correct) {
                        statsProvider.increaseWrongRequests();
                        throw new IllegalArgumentException("Wrong borders: " + inputParams);
                    }

                    cache.add(inputParams, root);
                    statsProvider.addRoot(root);
                    postgresQLDao.save(root);

                    return root;
                })
                .apply(inputParams);
    }


    public Integer calculate(InputParams inputParams) {
        return solve(inputParams);
    }

    /**
     * Method which is used for
     * solving multiple equalities.
     * @param params values for solving
     * @return {@link Collection} of calculated roots
     */
    public Collection<Integer> calculate(Collection<InputParams> params) {
        Executor executor = Executors.newCachedThreadPool();

        CompletableFuture<Collection<Integer>> completableFuture = CompletableFuture.supplyAsync(() -> {
            MyLogger.log(Level.WARN, "Current thread : " + Thread.currentThread().getName());
            return params
                    .stream()
                    .filter(this::isCorrectParams)
                    .map(e -> {
                        try {
                            return solve(e);
                        } catch (IllegalArgumentException exception) {
                            MyLogger.log(Level.ERROR, exception.getMessage());
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .toList();
        }, executor);

        try {
            return completableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}