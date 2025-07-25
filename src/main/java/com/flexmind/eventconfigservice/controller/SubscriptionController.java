package com.flexmind.eventconfigservice.controller;

import com.flexmind.eventconfigservice.api.SubscriptionApi;
import com.flexmind.eventconfigservice.api.dto.Subscription;
import com.flexmind.eventconfigservice.api.dto.SubscriptionCreateRequest;
import com.flexmind.eventconfigservice.api.dto.WebhookSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SubscriptionController implements SubscriptionApi {

    @Override
    public ResponseEntity<Subscription> createSubscription(SubscriptionCreateRequest subscriptionCreateRequest) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<WebhookSettings> getWebhookSettings() {
        log.info("Получение настроек webhook");
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
