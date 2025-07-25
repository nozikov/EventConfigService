package com.flexmind.eventconfigservice.exception;

import com.flexmind.eventconfigservice.api.dto.Error;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Error> handleEntityNotFound(EntityNotFoundException ex) {
        log.error("Объект не найден", ex);
        Error error = new Error()
                .code("NOT_FOUND")
                .message(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Error> handleConflict(DataIntegrityViolationException ex) {
        log.error("Конфликт данных", ex);
        Error error = new Error()
                .code("CONFLICT")
                .message("Нарушение целостности данных: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<Error> handleValidationExceptions(Exception ex) {
        log.error("Ошибка валидации", ex);
        Map<String, Object> details = new HashMap<>();

        if (ex instanceof MethodArgumentNotValidException validEx) {
            validEx.getBindingResult().getFieldErrors().forEach(error ->
                details.put(error.getField(), error.getDefaultMessage()));
        }

        Error error = new Error()
                .code("BAD_REQUEST")
                .message("Ошибка валидации данных")
                .details(details);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleGeneralException(Exception ex) {
        log.error("Внутренняя ошибка сервера", ex);
        Error error = new Error()
                .code("INTERNAL_ERROR")
                .message("Внутренняя ошибка сервера: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
