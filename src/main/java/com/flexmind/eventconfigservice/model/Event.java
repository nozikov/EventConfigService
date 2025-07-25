package com.flexmind.eventconfigservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * Represents an event in the system.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    private UUID id;
    private String type;
    private String source;
    private LocalDateTime timestamp;
    private Map<String, Object> payload;
}
