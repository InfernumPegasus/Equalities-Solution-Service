package com.example.service.process;

import com.example.service.cache.Cache;
import com.example.service.logger.MyLogger;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.example.service.controllers.MainController.context;

public class Solution {

    private static final Cache cache =
            context.getBean("cache", Cache.class);

    private static @Nullable Integer root;

    public static void calculateRoot(@NotNull InputParams inputParams) {
        // Trying to find root in cache
        root = cache.find(inputParams);

        if (root != null) {
            MyLogger.log(Level.WARN, "Value " + root + " found in cache!");
        } else {
            // If not found
            root = inputParams.getSecondValue() - inputParams.getFirstValue();

            if (root < inputParams.getLeftBorder() || root > inputParams.getRightBorder())
                throw new ArithmeticException("Root " + root + " is not in range!");

            // Adding { inputParams, root } to cache
            cache.add(inputParams, root);
        }
    }

    @Contract(pure = true)
    public static @NotNull Integer getRoot() {
        assert root != null;
        return root;
    }
}