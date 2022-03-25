package com.example.service.process;

import com.example.service.SpringConfig;
import com.example.service.cache.SolutionCache;
import com.example.service.logger.MyLogger;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Solution {

    private final SolutionCache cache;

    private final InputParams inputParams;

    private Integer root;

    public Solution(InputParams params) {
        var context = new AnnotationConfigApplicationContext(SpringConfig.class);
        cache = context.getBean("cache", SolutionCache.class);
        context.close();

        this.inputParams = params;
    }

    public void calculateRoot() {
        // Trying to find root in cache
        var temp = cache.find(inputParams);
        if (temp != null) {
            MyLogger.log(Level.WARN, "Value found in cache!");
            setRoot(temp);

            return;
        }

        // If not found
        temp = inputParams.getSecondValue() - inputParams.getFirstValue();

        if (temp < inputParams.getLeftBorder() || temp > inputParams.getRightBorder())
            throw new ArithmeticException("Root is not in range!");

        setRoot(temp);

        // Adding { inputParams, root } to cache
        cache.add(inputParams, root);
    }

    public Integer getRoot() {
        return root;
    }

    public void setRoot(@Nullable Integer root) {
        this.root = root;
    }

}