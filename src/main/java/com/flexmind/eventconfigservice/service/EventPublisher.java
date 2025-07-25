package com.flexmind.eventconfigservice.service;

import com.flexmind.eventconfigservice.model.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    private static final String EVENT_TOPIC = "event-notifications";

    public CompletableFuture<SendResult<String, Object>> publishEvent(Event event) {
        log.info("Publishing event: {}", event);
        
        String key = event.getId().toString();
        
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(EVENT_TOPIC, key, event);
        
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Event published successfully: {}", event.getId());
                log.debug("Event details: topic={}, partition={}, offset={}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            } else {
                log.error("Failed to publish event: {}", event.getId(), ex);
            }
        });
        
        return future;
    }
}
