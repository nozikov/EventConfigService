package com.flexmind.eventconfigservice.common.error;

import com.revision.api.common.error.handler.BusinessError;

import static org.slf4j.event.Level.WARN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@BusinessError(
        status = UNAUTHORIZED,
        errorCode = "authError",
        message = "Не верный логин или пароль"
)
public class AuthException extends BusinessErrorException {

    public AuthException(String cause) {
        logMessage(WARN, false, "Authenticate failed. Cause: {}", cause);
    }
}
