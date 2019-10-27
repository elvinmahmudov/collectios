package com.elvinmahmudov.collectios.exception;

public class EmptyObjectException extends RuntimeException {

    private static final String MESSAGE = "Operation is not allowed on an empty object.";

    public EmptyObjectException() {
        super(MESSAGE);
    }
}
