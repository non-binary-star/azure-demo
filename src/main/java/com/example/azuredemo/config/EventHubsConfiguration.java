package com.example.azuredemo.config;

import com.azure.spring.messaging.checkpoint.Checkpointer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.azure.spring.messaging.AzureHeaders.CHECKPOINTER;
import static com.azure.spring.messaging.AzureHeaders.PARTITION_KEY;
import static com.azure.spring.messaging.eventhubs.support.EventHubsHeaders.*;

@Configuration
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(value = "application.event-hubs.enabled", havingValue = "true")
public class EventHubsConfiguration {

    @Bean
    public Sinks.Many<Message<String>> sink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<Message<String>>> supply(Sinks.Many<Message<String>> sink) {
        return () -> {
            log.info("Log from supply");
            return sink.asFlux()
                    .doOnNext(m -> log.info("Sending message to Event Hubs {}", m))
                    .doOnError(t -> log.error("Error encountered", t));
        };
    }

    @Bean
    public Consumer<Message<String>> consume() {
        return message -> {
            MessageHeaders messageHeaders = message.getHeaders();
            Checkpointer checkpointer = (Checkpointer) messageHeaders.get(CHECKPOINTER);
            log.info("New message received: '{}', partition key: {}, sequence number: {}, offset: {}, enqueued time: {}",
                    message.getPayload(),
                    messageHeaders.get(PARTITION_KEY),
                    messageHeaders.get(SEQUENCE_NUMBER),
                    messageHeaders.get(OFFSET),
                    messageHeaders.get(ENQUEUED_TIME)
            );
            checkpointer.success()
                    .doOnSuccess(success -> log.info("Message '{}' successfully checkpointed",
                            message.getPayload()))
                    .doOnError(error -> log.error("Exception encountered", error))
                    .block();
        };
    }
}
