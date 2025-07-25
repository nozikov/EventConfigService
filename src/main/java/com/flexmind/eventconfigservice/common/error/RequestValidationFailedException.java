package com.flexmind.eventconfigservice.common.error;

import com.revision.api.common.error.handler.BusinessError;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.slf4j.event.Level.WARN;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@BusinessError(
        status = BAD_REQUEST,
        errorCode = "requestValidationFailedError",
        message = "Данные в запросе не соответствуют спецификации"
)
public class RequestValidationFailedException extends BusinessErrorException {

    public RequestValidationFailedException(MethodArgumentNotValidException ex) {
        super(ex);
        StringBuilder sb = new StringBuilder();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            sb.append("Request validation failed")
                    .append(". Object: ").append(error.getObjectName())
                    .append(". Message: ").append(error.getDefaultMessage());
            if (error instanceof FieldError) {
                sb.append(". Field: ").append(((FieldError) error).getField());
            }
            sb.append("\n");
        }
        logMessage(WARN, false, sb.toString());
        responseDebug = sb.toString();
    }

    public RequestValidationFailedException(MethodArgumentTypeMismatchException ex) {
        super(ex);
        logMessage(WARN, false,"Request validation failed. Cause: {} Type Mismatch for property: {}",
                ex.getMostSpecificCause().getMessage(),
                ex.getName());
    }

    public RequestValidationFailedException(HttpMessageNotReadableException ex) {
        super(ex);
        logMessage(WARN, false,"Request validation failed. Cause: {}", ex.getMostSpecificCause().getMessage());
    }

    public RequestValidationFailedException(HttpMediaTypeException ex) {
        super(ex);
        logMessage(WARN, false, "Request validation failed. Cause: {}", ex.getMessage());
    }

    public RequestValidationFailedException(ConstraintViolationException ex) {
        logMessage(WARN, false,"Request validation failed: Cause: {}", ex.getMessage());
    }

    public RequestValidationFailedException(String message) {
        logMessage(WARN, false,"Request validation failed. Cause: {}", message);
    }
}
