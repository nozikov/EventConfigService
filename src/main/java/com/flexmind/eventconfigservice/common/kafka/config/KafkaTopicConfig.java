package com.flexmind.eventconfigservice.common.kafka.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic eventNotificationTopic() {
        // Create a topic with 1 partition and replication factor of 1
        return new NewTopic("event-notifications", 1, (short) 1);
    }

    @Bean
    public NewTopic webhookDeliveryTopic() {
        // Create a topic with 1 partition and replication factor of 1
        return new NewTopic("webhook-deliveries", 1, (short) 1);
    }

    @Bean
    public NewTopic deadLetterQueueTopic() {
        // Create a topic with 1 partition and replication factor of 1
        return new NewTopic("dead-letter-queue", 1, (short) 1);
    }
}
