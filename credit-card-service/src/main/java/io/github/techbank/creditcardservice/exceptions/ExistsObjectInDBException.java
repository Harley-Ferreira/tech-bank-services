package io.github.techbank.creditcardservice.exceptions;

public class ExistsObjectInDBException extends RuntimeException {
    public ExistsObjectInDBException(String message) {
        super(message);
    }
}
