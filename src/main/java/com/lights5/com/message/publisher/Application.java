package com.lights5.com.message.publisher;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
@RequiredArgsConstructor
public class Application {

	private final AppConfig appConfig;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	NewTopic usersTopic() {

		return TopicBuilder.name(appConfig.getTopicName())
				.partitions(3)
				.replicas(2)
				.build();
	}
}
