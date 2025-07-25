package com.flexmind.eventconfigservice.common.error.handler;

import com.revision.api.common.error.ResponseValidationFailedException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Aspect
public class ControllerResponseValidator {

    private final Validator validator;

    public ControllerResponseValidator(Validator validator) {
        this.validator = validator;
    }

    @AfterReturning(pointcut = "execution(public org.springframework.http.ResponseEntity com.revision..*Controller.*(..))", returning = "result")
    public void validateResponse(JoinPoint joinPoint, Object result) {
        if (result instanceof ResponseEntity && ((ResponseEntity) result).getBody() != null) {
            validateResponse(joinPoint.getSignature().toShortString(), ((ResponseEntity) result).getBody());
        }
    }

    private void validateResponse(String method, Object result) {
        Set<ConstraintViolation<Object>> validationResults = validator.validate(result);
        if (validationResults.isEmpty() && result instanceof Collection) {
            validationResults = ((Collection<?>) result).stream()
                    .map(validator::validate)
                    .flatMap(Set::stream)
                    .collect(Collectors.toSet());
        }

        if (validationResults.size() > 0) {
            StringBuilder sb = new StringBuilder();

            for (ConstraintViolation<Object> error : validationResults) {
                sb.append(error.getPropertyPath()).append(" - ").append(error.getMessage()).append("\n");
            }

            String msg = sb.toString();
            throw new ResponseValidationFailedException(method, msg);
        }
    }
}
