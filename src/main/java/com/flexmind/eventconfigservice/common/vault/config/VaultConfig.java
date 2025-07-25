package com.flexmind.eventconfigservice.common.vault.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class VaultConfig {
    
    @Value("${database.username:postgres}")
    private String databaseUsername;
    
    @Value("${database.password:postgres}")
    private String databasePassword;
    
    @Value("${kafka.username:#{null}}")
    private String kafkaUsername;
    
    @Value("${kafka.password:#{null}}")
    private String kafkaPassword;
    
    @Value("${webhook.signing-key:#{null}}")
    private String webhookSigningKey;

    @EventListener(ApplicationReadyEvent.class)
    public void logConfiguration() {
        log.info("Vault configuration loaded");
        log.info("Database credentials configured: username={}, password={}", 
                databaseUsername, databasePassword != null ? "******" : "not set");
        
        if (kafkaUsername != null && kafkaPassword != null) {
            log.info("Kafka credentials configured: username={}, password=******", kafkaUsername);
        } else {
            log.info("Kafka credentials not configured from Vault");
        }
        
        if (webhookSigningKey != null) {
            log.info("Webhook signing key configured: {}", "******");
        } else {
            log.info("Webhook signing key not configured from Vault");
        }
    }
}