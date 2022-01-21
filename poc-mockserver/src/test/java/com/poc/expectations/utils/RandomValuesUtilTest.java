package com.poc.expectations.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RandomValuesUtilTest {

    @Test
    void testStringWithLettersAndNumber() {
        String randomValue = RandomValuesUtil.stringLettersAndNumber(10);

        char[] characters = randomValue.toCharArray();

        boolean containLetter = false;
        boolean containNumber = false;

        int index = 0;
        while (index < characters.length && (!containLetter || !containNumber)) {

            if (!containLetter && Character.isLetter(characters[index])) {
                containLetter = true;
            }

            if (!containNumber && Character.isDigit(characters[index])) {
                containNumber = true;
            }

            index++;
        }

        Assertions.assertTrue(containLetter);
        Assertions.assertTrue(containNumber);
    }

    @Test
    void testStringWithOnlyNumbers() {
        String randomValue = RandomValuesUtil.stringNumbers(10);
        boolean hasLetter = randomValue.chars().anyMatch(Character::isLetter);
        Assertions.assertFalse(hasLetter);
    }

}
