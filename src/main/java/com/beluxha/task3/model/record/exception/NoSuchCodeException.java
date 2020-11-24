package com.beluxha.task3.model.record.exception;

public class NoSuchCodeException extends Exception {
    private static final String MESSAGE = "No such game code: ";

    public NoSuchCodeException(int gameCode) {
        super(MESSAGE + gameCode);
    }
}
