package io.github.techbank.creditcardservice.exceptions;

public class ObjectNotFoundBDException extends RuntimeException {
    public ObjectNotFoundBDException(String message) {
        super(message);
    }
}
