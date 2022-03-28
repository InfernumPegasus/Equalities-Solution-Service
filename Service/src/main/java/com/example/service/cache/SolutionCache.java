package com.example.service.cache;

import com.example.service.process.InputParams;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.example.service.logger.MyLogger;

import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

public class SolutionCache {

    private static final Map<InputParams, Integer> solutions = new HashMap<>();

    public void add(@NotNull InputParams params, @NotNull Integer root) {
        if (!solutions.containsKey(params)) {
            solutions.put(params, root);
            MyLogger.log(Level.INFO, "Value " + params + "@" + root + " added to cache!");
        }
    }

    public @Nullable Integer find(@NotNull InputParams params) {
        if (solutions.containsKey(params))
            return solutions.get(params);

        MyLogger.log(Level.WARN, "Value " + params + " was not found in cache!");
        return null;
    }

    @Contract(pure = true)
    public static @NotNull String getStringCache() {
        return ("Cache:\n" + solutions);
    }

    @PreDestroy
    void preDestroy() {
        solutions.clear();
        MyLogger.log(Level.WARN, "Solution cache: map destroyed!");
    }
}
