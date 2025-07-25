package com.flexmind.eventconfigservice.common.error;


import com.flexmind.eventconfigservice.common.error.handler.BusinessError;

import static org.slf4j.event.Level.ERROR;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@BusinessError(
        status = INTERNAL_SERVER_ERROR,
        errorCode = "internalServerError",
        message = "Ошибка сервера"
)
public class UnknownPortalException extends BusinessErrorException {

    public UnknownPortalException(Throwable throwable) {
        super(throwable);
        logMessage(ERROR, true, "Portal server failure: Unhandled exception: {}", throwable.getMessage());
    }
}
