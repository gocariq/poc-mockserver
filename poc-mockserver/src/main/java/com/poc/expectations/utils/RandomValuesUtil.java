package com.poc.expectations.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomValuesUtil {

    private RandomValuesUtil() {}

    public static String stringLettersAndNumber(int length) {
        return RandomStringUtils.random(length, true, true);
    }

    public static String stringNumbers(int length) {
        return RandomStringUtils.random(length, false, true);
    }

}
