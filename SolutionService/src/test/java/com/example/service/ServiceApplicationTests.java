package com.example.service;

import com.example.service.input.InputParams;
import com.example.service.validation.InputParamsValidator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

class ServiceApplicationTests {

    @Test
    void areTwoSameObjectsEqual() {
        var s1 = new InputParams(10, 20, 199, 399);
        var s2 = new InputParams(10, 20, 199, 399);

        assert s1.equals(s2);
    }

    @Test
    void areTwoSameObjectsHaveSameHash() {
        var s1 = new InputParams(10, 20, 199, 399);
        var s2 = new InputParams(10, 20, 199, 399);

        assert s1.hashCode() == s2.hashCode();
    }

    @Test
    void isLeftBorderCorrectIfNull() {
        var params = new InputParams(10, 20, null, 1000);

        assert params.getLeftBorder() == Integer.MIN_VALUE;
    }

    @Test
    void bulkOperationsCounter() {

        var params = new LinkedList<InputParams>();

        var s1 = new InputParams(10, 645, null, null);
        var s2 = new InputParams(10, 45, null, null);
        var s3 = new InputParams(56, 20, null, null);

        params.add(s1);
        params.add(s2);
        params.add(s3);

        var count = params
                .stream()
                .filter(p -> p.getFirstValue() >= 10 && p.getSecondValue() <= 1000)
                .count();

        assert count == params.size();
    }

    @Test
    void isCollectionValidated() {
        ArrayList<InputParams> params = new ArrayList<>();
        params.add(new InputParams(null, 34, 23, 34));
        params.add(new InputParams(21, null, 23, 34));
        params.add(new InputParams(9, 34, null, null));
        params.add(new InputParams(null, null, null, null));

        var validated =
                InputParamsValidator.getValidByDefault(params);
        System.out.println(validated);

        assert validated.size() == 1;
    }

    @Test
    void isSingleParamsCorrect() {
        InputParams params = new InputParams(null, 23, 34, null);

        assert !InputParamsValidator.checkByDefault(params);
    }
}
