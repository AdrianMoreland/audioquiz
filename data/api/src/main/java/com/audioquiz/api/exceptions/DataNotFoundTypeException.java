package com.audioquiz.api.exceptions;

/**
 * Custom exception class for invalid login types.
 */
public class DataNotFoundTypeException extends RuntimeException {
    private String docId;
     /**
     * Constructs a new {@code DataNotFoundTypeException} with the specified detail message.
     *
     * @param message The detail message.
     */
    public DataNotFoundTypeException(String message) {
        super(message);
    }

    public DataNotFoundTypeException(String message, String docId) {
        super(message);
        this.docId = docId;
    }

    public String getDocId() {
        return docId;
    }

}