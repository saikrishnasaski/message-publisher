package com.lights5.com.message.publisher;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
class EventCounterMeter {

    private final MeterRegistry meterRegistry;
    private final Map<String, Counter> eventsCounter = new ConcurrentHashMap<>();

    @PostConstruct
    void init() {

        Arrays.stream(EventType.values())
                .forEach(eventType -> {
                    String eventName = eventType.name();
                    Counter counter = Counter.builder(eventName)
                            .tag("event_type", eventName)
                            .register(meterRegistry);

                    eventsCounter.put(eventName, counter);
                });
    }

    void incrementEventCounter(String eventType) {

        eventsCounter.entrySet()
                .stream()
                .filter(entry -> eventType.equals(entry.getKey()))
                .map(Map.Entry::getValue)
                .forEach(Counter::increment);
    }
}
