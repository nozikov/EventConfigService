package com.flexmind.eventconfigservice.listener;

import com.flexmind.eventconfigservice.model.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventListener {

    @KafkaListener(topics = "event-notifications", groupId = "${spring.application.name}")
    public void handleEventNotification(
            @Payload Event event,
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.OFFSET) Long offset) {
        
        log.info("Received event notification: id={}, type={}, source={}", 
                event.getId(), event.getType(), event.getSource());
        log.debug("Event details: topic={}, partition={}, offset={}, key={}", 
                topic, partition, offset, key);
        
        try {
            log.info("Processing event: {}", event);
            
            // TODO: Implement actual event processing logic
            
        } catch (Exception e) {
            log.error("Error processing event: {}", event.getId(), e);
        }
    }

    @KafkaListener(topics = "webhook-deliveries", groupId = "${spring.application.name}-webhook-delivery")
    public void handleWebhookDelivery(@Payload Event event) {
        log.info("Received webhook delivery request for event: {}", event.getId());

        try {
            // Example webhook delivery logic
            log.info("Delivering webhook for event: {}", event);
            
            // TODO: Implement actual webhook delivery logic
            
        } catch (Exception e) {
            log.error("Error delivering webhook for event: {}", event.getId(), e);
        }
    }

    @KafkaListener(topics = "dead-letter-queue", groupId = "${spring.application.name}-dlq")
    public void handleDeadLetterQueue(@Payload Event event) {
        log.warn("Received message in dead letter queue: {}", event.getId());
        
        try {
            log.info("Processing dead letter for event: {}", event);
            
            // TODO: Implement actual dead letter handling logic
            
        } catch (Exception e) {
            log.error("Error processing dead letter for event: {}", event.getId(), e);
        }
    }
}
