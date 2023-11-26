package io.github.techbank.creditcardservice;

import io.github.techbank.creditcardservice.exceptions.ExistsObjectInDBException;
import io.github.techbank.creditcardservice.exceptions.ObjectNotFoundBDException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class AppControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrors> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<String> errors = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        return ResponseEntity.badRequest().body(new ApiErrors(errors));
    }

    @ExceptionHandler(ExistsObjectInDBException.class)
    public ResponseEntity<ApiErrors> handleExistsObjectInDBException(ExistsObjectInDBException e) {
        return ResponseEntity.badRequest().body(new ApiErrors(e));
    }

    @ExceptionHandler(ObjectNotFoundBDException.class)
    public ResponseEntity<ApiErrors> handleObjectNotFoundBDException(ObjectNotFoundBDException e) {
        return ResponseEntity.badRequest().body(new ApiErrors(e));
    }

    private record ApiErrors(List<String> errors) {
        private ApiErrors(Exception exception) {
            this(Arrays.asList(exception.getMessage()));
        }
    }
}
