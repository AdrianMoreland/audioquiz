package com.audioquiz.api.exceptions;

/**
 * Custom exception class for invalid login types.
 */
public class InvalidLoginTypeException extends RuntimeException {
    /**
     * Constructs a new {@code InvalidLoginTypeException} with the specified detail message.
     *
     * @param message The detail message.
     */
    public InvalidLoginTypeException(String message) {
        super(message);
    }
}