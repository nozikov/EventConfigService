package com.flexmind.eventconfigservice.controller;

import com.flexmind.eventconfigservice.api.EventConfigApi;
import com.flexmind.eventconfigservice.api.dto.EventConfig;
import com.flexmind.eventconfigservice.api.dto.EventConfigCreateRequest;
import com.flexmind.eventconfigservice.api.dto.EventConfigUpdateRequest;
import com.flexmind.eventconfigservice.api.dto.PagedEventConfigList;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EventConfigController implements EventConfigApi {

    @Override
    public ResponseEntity<EventConfig> createEventConfig(EventConfigCreateRequest eventConfigCreateRequest) {
        log.info("Создание конфигурации события: {}", eventConfigCreateRequest);
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<PagedEventConfigList> getEventConfigList(Optional<String> eventType, Optional<String> source,
                                                                   Optional<Boolean> enabled, Optional<@Min(0) Integer> page,
                                                                   Optional<@Min(1) @Max(100) Integer> size) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<EventConfig> updateEventConfig(UUID id, EventConfigUpdateRequest eventConfigUpdateRequest) {
        log.info("Обновление конфигурации события с id={}: {}", id, eventConfigUpdateRequest);
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
