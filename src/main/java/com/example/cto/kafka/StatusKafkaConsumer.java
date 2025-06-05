package com.example.cto.kafka;

import com.example.cto.service.RequestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class StatusKafkaConsumer {

    private final RequestService requestService;

    @KafkaListener(topics = "statusСhange", groupId = "сto-service")
    public void handleStatusChange(String message) {
        try {
            StatusChangeEvent event = new ObjectMapper().readValue(message, StatusChangeEvent.class);
            log.info("Kafka consumed: {}", event);
            requestService.changeStatus(event.requestId(), event.newStatus(), event.changedBy(), event.reason());
        } catch (Exception e) {
            log.error("Failed to process Kafka event", e);
        }
    }
}
