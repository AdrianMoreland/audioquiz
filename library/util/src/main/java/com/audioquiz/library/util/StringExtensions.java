package com.audioquiz.library.util;

import android.widget.EditText;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringExtensions {
    private static final Random random = new Random();
    private static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String randomId() {
        return UUID.randomUUID().toString();
    }


    public static String getRandomText(int length) {
        return ThreadLocalRandom.current()
                .ints(length, 0, CHARS.length())
                .mapToObj(CHARS::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    private static final List<String> wordPool = List.of(
            "steward", "brother", "sister", "man", "woman", "goose", "surface",
            "boom", "rumor", "mastermind", "splurge", "flash", "picture",
            "parallel", "needle", "mobile", "television", "differ", "trace"
    );


    public static String getRandomUsernameString() {
        Random random = new Random();
        int firstNameIndex = random.nextInt(firstNames.size());
        int surnameIndex = random.nextInt(surnames.size());
        return firstNames.get(firstNameIndex) + " " + surnames.get(surnameIndex);
    }

    public static final List<String> firstNames = List.of(
            "Homer", "Marge", "Bart", "Lisa", "Maggie", "Ned", "Moe",
            "Barney", "Apu", "Seymour", "Milhouse", "Nelson", "Ralph",
            "Krusty", "Sideshow", "Montgomery", "Waylon", "Edna", "Selma",
            "Otto", "Kirk", "Luann", "Agnes", "Maude", "Rod", "Todd",
            "Carl", "Lenny", "Kent"
    );

    public static final List<String> surnames = List.of(
            "Simpson", "Flanders", "Skinner", "Burns", "Van Houten", "Wiggum",
            "Szyslak", "Gumble", "Nahasapeemapetilon", "Prince", "Lovejoy", "Krabappel",
            "Quimby", "Brockman", "Hibbert", "Carlson", "Ziff", "Spuckler", "Powers",
            "Bouvier", "Groundskeeper", "Terwilliger", "Chalmers", "Smithers", "Riviera",
            "Leonard", "Winfield", "Powell", "McClure"
    );

    public static String randomWords(int length) {
        return IntStream.range(0, length)
                .mapToObj(i -> wordPool.get(random.nextInt(wordPool.size())))
                .collect(Collectors.joining(" "));
    }

    public static String randomText(int length) {
        return getRandomText(length);
    }

    public static String getStringValue(Number value) {
        return String.valueOf(value != null ? value : "N/A");
    }

    public static String getString(Map<?, ?> map, String fieldName) {
        Object value = map.get(fieldName);
        if (value instanceof String) {
            return (String) value;
        } else {
            throw new IllegalArgumentException("Value is not a String for key: " + fieldName);
        }
    }

    /**
     * Safely get a value from a Map, handling null cases and providing a default value.
     */
    public static <T> T getOrDefault(Map<String, Object> map, String key, Class<T> clazz, T defaultValue) {
        return Optional.ofNullable(clazz.cast(map.get(key)))
                .orElse(defaultValue);
    }

    public static <T> Optional<T> getValueFromMapOrElse(Map<String, Object> map, String key, Class<T> clazz) {
        return Optional.ofNullable(clazz.cast(map.get(key)));
    }

    public static String getText(EditText editText) {
        if (editText != null && editText.getText() != null) {
            return editText.getText().toString();} else {
            return "null";
        }
    }

    public static String getNotNullString(String text) {
        if (text != null) {
            return text;
        } else {
            return "null";
        }
    }
}