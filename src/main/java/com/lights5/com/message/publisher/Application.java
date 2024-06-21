package com.lights5.com.message.publisher;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
@RequiredArgsConstructor
public class Application implements CommandLineRunner {

	private final AppConfig appConfig;
	private final KafkaProperties kafkaProperties;

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

	@Override
	public void run(String... args) throws Exception {


		System.out.println("Logging ProducerConfig....");
		System.out.println(kafkaProperties.getProducer().getProperties().toString());

		kafkaProperties.getProperties()
				.forEach((k, v) -> System.out.println(k + " ::: " + v));


	}
}
