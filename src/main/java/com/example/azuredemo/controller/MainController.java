package com.example.azuredemo.controller;

import com.example.azuredemo.service.Producer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final Producer producer;

    @PostMapping("/produce/{messageId}")
    public ResponseEntity<?> produce(@PathVariable String messageId) {
        log.info("Received request on /produce/{}", messageId);
        producer.produce(messageId);
        return ResponseEntity.noContent().build();
    }
}
