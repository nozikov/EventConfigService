package com.flexmind.eventconfigservice.common.error;

import com.revision.api.common.error.handler.BusinessError;

import static org.slf4j.event.Level.ERROR;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@BusinessError(
        status = INTERNAL_SERVER_ERROR,
        errorCode = "badSqlError",
        message = "Ошибка выполнения SQL запроса"
)
public class BadSqlException extends BusinessErrorException {

    public BadSqlException(Throwable throwable) {
        super(throwable.getMessage(), throwable);
        logMessage(ERROR, true, "API module SQL error: " + throwable.getMessage());
    }
}
