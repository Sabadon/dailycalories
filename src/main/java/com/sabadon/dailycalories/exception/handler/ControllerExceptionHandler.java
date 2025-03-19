package com.sabadon.dailycalories.exception.handler;

import com.sabadon.dailycalories.dto.error.ApiErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleNotFound(final EntityNotFoundException ex) {
        return new ApiErrorResponse("NOT_FOUND", ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        final Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ApiErrorResponse("VALIDATION_FAILED", "Invalid request data", errors);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ApiErrorResponse handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        final String rootMsg = ex.getRootCause().getMessage();
        if (rootMsg.contains("users_email_key")) {
            return new ApiErrorResponse(
                    "DUPLICATE_EMAIL",
                    "Email already exists"
            );
        }
        log.error("Unexpected data conflict: ", ex);
        return new ApiErrorResponse("DATA_CONFLICT", "Database error");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiErrorResponse handleAllExceptions(Exception ex) {
        log.error("Unexpected error: ", ex);
        return new ApiErrorResponse("INTERNAL_ERROR", "Internal server error");
    }

}
