package com.flexmind.eventconfigservice.common.error;

import lombok.Getter;
import lombok.NonNull;
import org.slf4j.event.Level;

import static org.slf4j.helpers.MessageFormatter.arrayFormat;

@Getter
public abstract class LogBusinessErrorException extends RuntimeException {

    private Level logLevel;
    private String logMessage;
    private boolean showTrace;

    public LogBusinessErrorException() {
        super("");
    }

    public LogBusinessErrorException(Throwable throwable) {
        super(throwable.getMessage(), throwable);
    }

    public LogBusinessErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Вывести сообщение в лог при обработке ошибки
     *
     * @param logLevel уровень ошибки при логгировании
     * @param showTrace необходимость показывать stacktrace в логах
     * @param logMessage текс ошибки
     */
    protected void logMessage(@NonNull Level logLevel, boolean showTrace, @NonNull String logMessage, Object... arguments) {
        this.logLevel = logLevel;
        this.logMessage = formatMessage(logMessage, arguments);
        this.showTrace = showTrace;
    }

    protected static String formatMessage(String message, Object... arguments){
        return arrayFormat(message, arguments).getMessage();
    }
}
