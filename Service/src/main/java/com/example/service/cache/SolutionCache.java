package com.example.service.cache;

import com.example.service.process.InputParams;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SolutionCache {
    private static final Map<InputParams, Integer> solutions = new HashMap<>();

    public static void addToMap(@NotNull InputParams params, Integer root) {
        if (!solutions.containsKey(params))
            solutions.put(params, root);
    }

    public static @Nullable Integer findInCache(@NotNull InputParams params) {
        if (solutions.containsKey(params))
            return solutions.get(params);

        return null;
    }

    @Contract(pure = true)
    public static @NotNull String getCache() {
        return ("Cache:\n" + solutions);
    }
}
