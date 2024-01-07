package io.github.techbank.creditappraiserservice;

import feign.FeignException;
import io.github.techbank.creditappraiserservice.exceptions.ObjectNotFoundFeignException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class AppControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrors> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<String> errors = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        return ResponseEntity.badRequest().body(new ApiErrors(errors));
    }

    @ExceptionHandler(FeignException.FeignClientException.class)
    public ResponseEntity<ApiErrors> handleFeignClientException(FeignException.FeignClientException e) {
        if (e.status() == HttpStatus.NOT_FOUND.value()) {
            return ResponseEntity.ok(new ApiErrors("Ocorreu um erro, tente novamente mais tarde."));
        } else {
            return ResponseEntity.ok(new ApiErrors("Ocorreu um erro, tente novamente mais tarde."));
        }
    }

    @ExceptionHandler(ObjectNotFoundFeignException.class)
    public ResponseEntity<ApiErrors> handleObjectNotFoundFeignException(ObjectNotFoundFeignException e) {
        return ResponseEntity.badRequest().body(new ApiErrors(e.getMessage()));
    }

    public record ApiErrors(List<String> errors) {
        private ApiErrors(String message) {
            this(List.of(message));
        }
    }
}
