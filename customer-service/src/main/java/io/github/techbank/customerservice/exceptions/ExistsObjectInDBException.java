package io.github.techbank.customerservice.exceptions;

public class ExistsObjectInDBException extends RuntimeException {
    public ExistsObjectInDBException(String message) {
        super(message);
    }
}
