package com.flexmind.eventconfigservice.common.error.handler;

import org.springframework.http.HttpStatus;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BusinessError {

    HttpStatus status() default HttpStatus.BAD_REQUEST;

    /**
     * Business error's code
     */
    String errorCode() default "";

    /**
     * Business error's message
     */
    String message() default "";

    /**
     * Debug information
     */
    String debug() default "";

    /**
     * Empty errorCode
     */
    boolean isEmptyErrorCode() default false;
}
