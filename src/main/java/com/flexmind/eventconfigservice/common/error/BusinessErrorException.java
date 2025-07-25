package com.flexmind.eventconfigservice.common.error;

import com.flexmind.eventconfigservice.common.error.handler.BusinessError;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.slf4j.event.Level;
import org.springframework.core.annotation.AnnotatedElementUtils;

import static org.springframework.util.StringUtils.hasText;

@NoArgsConstructor
@Getter
public abstract class BusinessErrorException extends LogBusinessErrorException {

    protected String responseMessage;
    protected String responseDebug;

    public BusinessErrorException(Throwable throwable) {
        super(throwable);
    }

    public BusinessErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Вывести сообщение в лог при обработке ошибки
     *
     * @param logLevel уровень ошибки при логгировании
     * @param showTrace необходимость показывать stacktrace в логах
     * @param logMessage текс ошибки
     */
    @Override
    protected void logMessage(@NonNull Level logLevel, boolean showTrace, @NonNull String logMessage, Object... arguments) {
        super.logMessage(logLevel, showTrace, logMessage, arguments);
        BusinessError businessError = AnnotatedElementUtils.findMergedAnnotation(this.getClass(), BusinessError.class);
        if (businessError == null || !hasText(businessError.debug())) {
            responseDebug = getLogMessage();
        }
    }
}
