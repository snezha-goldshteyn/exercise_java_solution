package com.exercise.quotes.exceptions;

public class QuoteException extends RuntimeException {
    private int errorCode;

    public QuoteException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
