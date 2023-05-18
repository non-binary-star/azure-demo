package com.example.azuredemo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(value = "application.event-hubs.enabled", havingValue = "true")
public class EventHubsProducer implements Producer {

    private final Sinks.Many<Message<String>> sink;

    @Override
    public void produce(final String messageId) {
        log.info("Emitting message {}", messageId);
        sink.emitNext(MessageBuilder.withPayload(messageId).build(), Sinks.EmitFailureHandler.FAIL_FAST);
    }
}
