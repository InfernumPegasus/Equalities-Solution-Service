package com.example.service.validation;

import com.example.service.input.InputParams;
import java.util.Collection;
import java.util.function.Predicate;

public class InputParamsValidator {

    public static boolean checkByPredicate(
            InputParams params,
            Predicate<InputParams> predicate
    ) {
        return predicate.test(params);
    }

    public static boolean checkByDefault(
            InputParams params
    ) {
        Predicate<InputParams> predicate = param ->
                param.getFirstValue() != null &&
                param.getSecondValue() != null;

        return checkByPredicate(params, predicate);
    }

    public static Collection<InputParams> getValidByPredicate(
            Collection<InputParams> params,
            Predicate<InputParams> predicate
    ) {
        return params.stream()
                .filter(predicate)
                .toList();
    }

    public static Collection<InputParams> getValidByDefault(
            Collection<InputParams> params
    ) {
        Predicate<InputParams> predicate = param ->
                param.getFirstValue() != null && param.getSecondValue() != null;

        return getValidByPredicate(params, predicate);
    }

}
