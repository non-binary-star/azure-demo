package com.example.azuredemo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(value = "application.event-hubs.enabled", havingValue = "false")
public class NoopProducer implements Producer {

    @Override
    public void produce(String messageId) {
        log.info("Noop produce called for message {}", messageId);
    }
}
