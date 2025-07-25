package com.flexmind.eventconfigservice.common.error;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@BusinessError(
        status = FORBIDDEN,
        errorCode = "Forbidden",
        message = "Доступ запрещен"
)
public class ForbiddenException extends BusinessErrorException {

    public ForbiddenException() {
    }

    public ForbiddenException(String message) {
        responseMessage = message;
    }
}
