package com.lights5.com.message.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.micrometer.KafkaTemplateObservation;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
class UsersService {

    private final AppConfig appConfig;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, EventPayload> kafkaTemplate;
    private final EventCounterMeter eventCounterMeter;

    public void publishMessage(User user, EventType eventType) {

        try {

            var userCreatedEventPayload = objectMapper.writeValueAsString(user);
            var eventPayload = new EventPayload(eventType, userCreatedEventPayload);
            System.out.println("Main Flow " + Thread.currentThread().getName());
            kafkaTemplate.send(appConfig.getTopicName(), "1", eventPayload)
                            .thenAcceptAsync(sendResult -> {
                                System.out.println("After Result " + Thread.currentThread().getName());
                                Optional.ofNullable(sendResult.getProducerRecord())
                                        .map(ProducerRecord::value)
                                        .map(EventPayload::eventType)
                                        .map(EventType::name)
                                        .ifPresent(eventName -> eventCounterMeter.incrementEventCounter(eventName));
                            });

        }
        catch (JsonProcessingException ex) {

            log.error("Exception occurred in processing JSON {}", ex.getMessage());
        }
    }
}
