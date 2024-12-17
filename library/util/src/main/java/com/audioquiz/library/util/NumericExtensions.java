package com.audioquiz.library.util;

import java.util.Random;

public class NumericExtensions {

    private static final String DEFAULT_FORMAT = "yyyy-MM-dd";

    private NumericExtensions() {
        // Private constructor to prevent instantiation
    }

    public static int toInt(Object value) {
        if (value instanceof Integer integer) {
            return integer;
        } else if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Cannot convert to int: " + value, e);
            }
        } else if (value instanceof Long) {
            return ((Long) value).intValue();
        } else {
            throw new IllegalArgumentException("Unsupported type for int conversion: " + value.getClass());
        }
    }

    public static double roundTo(double value, int numberOfDecimals) {
        double factor = Math.pow(10.0, numberOfDecimals);
        return Math.round(value * factor) / factor;
    }

    public static double randomNumber(double from, double until, int numberOfDecimals) {
        Random random = new Random();
        double randomValue = from + (until - from) * random.nextDouble();
        return roundTo(randomValue, numberOfDecimals);
    }

    public static double randomNumber(double from, double until) {
        return randomNumber(from, until, 1);
    }

    public static double randomNumber() {
        return randomNumber(1.0, 5.0, 1);
    }
}
