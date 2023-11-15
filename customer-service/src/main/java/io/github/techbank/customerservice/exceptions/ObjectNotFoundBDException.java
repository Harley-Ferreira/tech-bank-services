package io.github.techbank.customerservice.exceptions;

public class ObjectNotFoundBDException extends RuntimeException {

    public ObjectNotFoundBDException(String message) {
        super(message);
    }
}
