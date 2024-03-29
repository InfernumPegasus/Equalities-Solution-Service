package com.example.service.services;

import com.example.service.cache.CacheService;
import com.example.service.dao.PostgresQLDaoImpl;
import com.example.service.input.InputParams;
import com.example.service.logger.MyLogger;
import com.example.service.stats.StatsProvider;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Supplier;


/**
 * Service which is used for solving
 * equalities from provided params.
 */
@Service
public class SolutionService {
    private final PostgresQLDaoImpl postgresQLDao;
    private final CacheService cache;
    private final StatsProvider statsProvider;

    @Autowired
    public SolutionService(
            PostgresQLDaoImpl postgresQLDao,
            CacheService cache,
            StatsProvider statsProvider
    ) {
        this.postgresQLDao = postgresQLDao;
        this.cache = cache;
        this.statsProvider = statsProvider;
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
    private Integer calculateRoot(
            InputParams params
    ) {
        // Increasing requests counter
        statsProvider.increaseTotalRequests();

        if (cache.contains(params)) {
            MyLogger.warn("Root for " + params + " found in cache!");
            return cache.get(params);
        }

        MyLogger.warn("Root for " + params + " was not found in cache!");

        int root = params.getSecondValue() - params.getFirstValue();

        boolean correct = root >= params.getLeftBorder() && root <= params.getRightBorder();

        if (!correct) {
            statsProvider.increaseWrongRequests();
            StringFormattedMessage message =
                    new StringFormattedMessage(
                            "Root %d is out of borders [%d, %d]!",
                            root,
                            params.getLeftBorder(),
                            params.getRightBorder()
                    );
            throw new IllegalArgumentException(message.getFormattedMessage());
        }

        return root;
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

        Executor executor = Executors.newFixedThreadPool(4);

        return params.stream()
                .map(item -> {
                    MyLogger.warn("Thread: " + Thread.currentThread().getName());
                    return CompletableFuture.supplyAsync(calculateRootSupplier(item), executor);
                })
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .peek(root -> {
                    // Saving to db
                    MyLogger.warn("Saving to db...");
                    postgresQLDao.save(root);
                    // Adding to statistics
                    MyLogger.info("Adding to stats...");
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
    private Supplier<Integer> calculateRootSupplier(InputParams inputParams) {
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