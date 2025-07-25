package com.flexmind.eventconfigservice.common.vault.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class SecretService {

    private final Environment environment;

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