package com.flexmind.eventconfigservice.controller;

import com.flexmind.eventconfigservice.api.UiApi;
import com.flexmind.eventconfigservice.api.dto.GroupedEventConfigListInner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UiController implements UiApi {

    @Override
    public ResponseEntity<List<GroupedEventConfigListInner>> getGroupedEventConfigList() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
