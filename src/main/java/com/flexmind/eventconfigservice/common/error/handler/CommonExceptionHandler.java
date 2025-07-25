package com.flexmind.eventconfigservice.common.error.handler;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.flexmind.eventconfigservice.api.dto.Error;
import com.flexmind.eventconfigservice.common.error.*;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.util.Assert;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.nio.file.AccessDeniedException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Order
@RestControllerAdvice(basePackages = "com.revision.api")
public class CommonExceptionHandler {

    @ExceptionHandler(BusinessErrorException.class)
    public final ResponseEntity<Error> handleBusinessException(BusinessErrorException ex) {
        BusinessError businessError = AnnotatedElementUtils.findMergedAnnotation(ex.getClass(), BusinessError.class);
        Assert.notNull(businessError, "Can't find BusinessError annotation");
        writeLogMessages(ex);
        return getResponseFromBusinessError(businessError, ex);
    }

    private ResponseEntity<Error> getResponseFromBusinessError(BusinessError businessError, BusinessErrorException ex) {
        if (hasText(businessError.errorCode()) || businessError.isEmptyErrorCode()) {
            Error err = new Error();
            err.setApplicationErrorCode(businessError.errorCode());
            err.setMessage(!hasText(ex.getResponseMessage()) ? businessError.message() : ex.getResponseMessage());
            return new ResponseEntity<>(err, businessError.status());
        }
        return new ResponseEntity<>(businessError.status());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Error> handleAccessDeniedException(AccessDeniedException ex) {
        return handleBusinessException(new ForbiddenException(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return handleBusinessException(new RequestValidationFailedException(ex));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Error> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        return handleBusinessException(new RequestValidationFailedException(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Error> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return handleBusinessException(new RequestValidationFailedException(ex));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Error> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return handleBusinessException(new RequestValidationFailedException(ex));
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    public ResponseEntity<Error> handleHttpMediaTypeException(HttpMediaTypeException ex) {
        return handleBusinessException(new RequestValidationFailedException(ex));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Error> handleDataAccessException(DataAccessException ex) {
        return handleBusinessException(new BadSqlException(ex));
    }

    @ExceptionHandler(CannotCreateTransactionException.class)
    public ResponseEntity<Error> handleCannotCreateTransactionException(CannotCreateTransactionException ex) {
        return handleBusinessException(new DbConnectionFailedException(ex));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Error> handleConstraintViolationException(ConstraintViolationException ex) {
        return handleBusinessException(new RequestValidationFailedException(ex));
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public final ResponseEntity<Error> handleMissingServletRequestPartException(MissingServletRequestPartException ex) {
        return handleBusinessException(new RequestValidationFailedException(ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Error> handleRuntimeException(RuntimeException ex) {
        log.error("Runtime exception: {}", ex.getMessage(), ex);
        Error err = new Error();
        err.setApplicationErrorCode("internalServerError");
        err.setMessage("Ошибка сервера");
        return new ResponseEntity<>(err, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleAnyException(Exception ex) {
        return handleBusinessException(new UnknownPortalException(ex));
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<Error> handleConversationException(HttpMessageConversionException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidDefinitionException) {
            if (cause.getCause() != null && cause.getCause() instanceof RequestValidationFailedException)
                return handleBusinessException((RequestValidationFailedException) cause.getCause());
        }
        return handleAnyException(ex);
    }

    protected void writeLogMessages(LogBusinessErrorException ex) {
        if (ex.getLogLevel() != null) {
            Exception exceptionForLogging = ex.isShowTrace() ? ex : null;
            switch (ex.getLogLevel()) {
                case ERROR
                        -> log.error(ex.getLogMessage(), exceptionForLogging);
                case WARN
                        -> log.warn(ex.getLogMessage(), exceptionForLogging);
                case INFO
                        -> log.info(ex.getLogMessage(), exceptionForLogging);
                case DEBUG
                        -> log.debug(ex.getLogMessage(), exceptionForLogging);
                case TRACE
                        -> log.trace(ex.getLogMessage(), exceptionForLogging);
                default
                        -> throw new UnsupportedOperationException("Incorrect logging level", ex);
            }
        }
    }
}
