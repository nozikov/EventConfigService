package com.flexmind.eventconfigservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SecretService {

    private final Environment environment;

    @Autowired
    public SecretService(Environment environment) {
        this.environment = environment;
    }

    public String getSecret(String key) {
        try {
            String value = environment.getProperty(key);
            if (value != null) {
                log.debug("Retrieved secret for key: {}", key);
                return value;
            } else {
                log.warn("No secret found for key: {}", key);
                return null;
            }
        } catch (Exception e) {
            log.error("Error retrieving secret for key: {}", key, e);
            return null;
        }
    }

    public String getSecret(String key, String defaultValue) {
        String value = getSecret(key);
        return value != null ? value : defaultValue;
    }
}