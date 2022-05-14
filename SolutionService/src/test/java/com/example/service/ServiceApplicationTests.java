package com.example.service;

import com.example.service.input.InputParams;
import org.junit.jupiter.api.Test;
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

        assert params.leftBorder() == Integer.MIN_VALUE;
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
                .filter(p -> p.firstValue() >= 10 && p.secondValue() <= 1000)
                .count();

        assert count == params.size();
    }
}
