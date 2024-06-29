package com.lights5.com.message.publisher;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationEventHandler {

    private final MeterRegistry meterRegistry;
    private final Path path = Paths.get("src/main/resources/file.txt");

    @EventListener
    void handleGracefulShutdown(ContextClosedEvent event) {

        log.info("Handling Graceful Shutdown");

        Arrays.stream(EventType.values())
                .map(EventType::name)
                .forEach(eventName -> {

                    double count = meterRegistry.get(eventName).counter().count();
                    try {
                        Files.write(path, String.valueOf(count).getBytes(), StandardOpenOption.WRITE);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @EventListener
    void handleApplicationStartUpEvent(ApplicationStartedEvent event) throws IOException {

        log.info("Handling Application Started Event");
        String value = Files.readString(path);

        if (!value.isBlank()) {

            Arrays.stream(EventType.values())
                    .map(EventType::name)
                    .forEach(eventName -> {

                        meterRegistry.get(eventName).counter().increment(Double.valueOf(value));
                    });
        }
    }
}
