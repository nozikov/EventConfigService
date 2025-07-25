package com.flexmind.eventconfigservice.common.error;

import com.revision.api.common.error.handler.BusinessError;

import static org.slf4j.event.Level.ERROR;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@BusinessError(
        status = INTERNAL_SERVER_ERROR,
        errorCode = "unknownInternalError",
        message = "Внутренняя ошибка модуля API"
)
public class DbConnectionFailedException extends BusinessErrorException {

    public DbConnectionFailedException(Throwable throwable) {
        super(throwable);
        logMessage(ERROR, true, "API module database connection failure");
    }
}