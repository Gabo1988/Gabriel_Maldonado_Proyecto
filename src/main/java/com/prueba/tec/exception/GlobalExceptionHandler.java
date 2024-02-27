package com.prueba.tec.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MissingServletRequestParameterException ex) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("error", "Missing parameter");
        errors.put("message", ex.getMessage());
        errors.put("status", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("error", "Bad Request");
        errors.put("message", ex.getMessage());
        errors.put("status", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatchExceptions(MethodArgumentTypeMismatchException ex) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("error", "Invalid argument type");
        errors.put("message", "Failed to convert value '" + ex.getValue() + "' to type '" + ex.getRequiredType().getSimpleName() + "'");
        errors.put("status", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleDataNotFoundException(DataNotFoundException ex) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("error", "Not Found");
        errors.put("message", ex.getMessage());
        errors.put("status", HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }
}
