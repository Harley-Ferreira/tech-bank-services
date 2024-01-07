package io.github.techbank.creditappraiserservice.exceptions;

public class ObjectNotFoundFeignException extends RuntimeException {
    public ObjectNotFoundFeignException(String message) {
        super(message);
    }
}
