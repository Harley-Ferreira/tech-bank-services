package io.github.techbank.creditcardservice;

import io.github.techbank.creditcardservice.exceptions.ExistsObjectInDBException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class AppControllerAdvice {

    @ExceptionHandler(ExistsObjectInDBException.class)
    public ResponseEntity handleExistsObjectInDBException(ExistsObjectInDBException e) {
        return ResponseEntity.badRequest().body(new ApiErrors(e));
    }

    private record ApiErrors(List<String> errors) {
        private ApiErrors(Exception exception) {
            this(Arrays.asList(exception.getMessage()));
        }
    }
}
