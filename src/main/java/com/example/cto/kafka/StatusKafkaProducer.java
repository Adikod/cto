package com.example.cto.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class StatusKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "statusChange";

    public void sendStatusChange(StatusChangeEvent event) {
        try {
            String json = new ObjectMapper().writeValueAsString(event);
            kafkaTemplate.send(TOPIC, json);
            log.info("Sent Kafka event: {}", json);
        } catch (Exception e) {
            log.error("Failed to send Kafka event", e);
        }
    }
}
