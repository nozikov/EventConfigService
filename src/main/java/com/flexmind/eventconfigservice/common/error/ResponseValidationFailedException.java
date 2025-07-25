package com.flexmind.eventconfigservice.common.error;

import com.revision.api.common.error.handler.BusinessError;

import static org.slf4j.event.Level.ERROR;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@BusinessError(
        status = BAD_REQUEST,
        errorCode = "responseValidationFailedError",
        message = "Данные в ответе не соответствуют спецификации"
)
public class ResponseValidationFailedException extends BusinessErrorException {

    public ResponseValidationFailedException(String method, String message) {
        logMessage(ERROR, true,"Response validation failed on method {}. Cause: {}", method, message);
    }
}
