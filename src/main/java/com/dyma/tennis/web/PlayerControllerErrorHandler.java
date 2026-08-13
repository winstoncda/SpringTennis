package com.dyma.tennis.web;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PlayerControllerErrorHandler {

        @ExceptionHandler(NoSuchElementException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public void handleNoElementException() {}

        @ExceptionHandler(MethodArgumentNotValidException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
            var errors = new HashMap<String, String>();
            ex.getBindingResult().getFieldErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return errors;
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public Map<String, String> handleInvalidFormatException(HttpMessageNotReadableException ex) {
            var errors = new HashMap<String, String>();
            String message = ex.getMessage();

            // Détecter si c'est un problème de format de date
            if (message != null && message.contains("LocalDate")) {
                errors.put("dateOfBirth", "Le format de la date doit être YYYY-MM-DD (exemple: 2024-08-13)");
            } else {
                errors.put("error", "Format de données invalide");
            }
            return errors;
        }
}
