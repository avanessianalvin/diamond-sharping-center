package com.vozni.springjwt.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().body(
                e.getBindingResult().getFieldErrors().stream()
                        .collect(java.util.stream.Collectors.toMap(FieldError::getField,FieldError::getDefaultMessage)));
    }


    // Handle custom exceptions
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException e) {
        System.err.println("Exception To The Client:");
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    // Catch-all for all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAll(Exception e) {
        e.printStackTrace();
        // Return empty or generic message
        //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
