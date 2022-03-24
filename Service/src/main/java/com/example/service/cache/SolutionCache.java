package com.example.service.cache;

import com.example.service.process.InputParams;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.example.service.logger.MyLogger ;

import java.util.HashMap;
import java.util.Map;

/**
 Данный класс сделан без использования Dependency Injection
 и аннотаций @Bean/@Component, т.к. для внедрения
 потребовалось бы создавать объект данного класса
 при вызове метода getBean(), и каждый раз взаимодействовать
 с созданным объектом, что привело бы к лишним затратам ресурсов.
 Поэтому было принято решение обойтись static-полем с контейнером.
**/

public class SolutionCache {

    private static final Map<InputParams, Integer> solutions = new HashMap<>();

    public static void add(@NotNull InputParams params, @NotNull Integer root) {
        if (!solutions.containsKey(params)) {
            solutions.put(params, root);
            MyLogger.log(Level.INFO, "Value added to cache!");
        }
    }

    public static @Nullable Integer find(@NotNull InputParams params) {
        if (solutions.containsKey(params)) {
            MyLogger.log(Level.WARN, "Value found in cache!");

            return solutions.get(params);
        }
        MyLogger.log(Level.WARN, "Value was not found in cache!");

        return null;
    }

    @Contract(pure = true)
    public static @NotNull String getCache() {
        return ("Cache:\n" + solutions);
    }
}
