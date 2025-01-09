package com.recipe.management.exception;

public class DataServiceException extends RuntimeException {
    public DataServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataServiceException(String message) {
        super(message);
    }
}
