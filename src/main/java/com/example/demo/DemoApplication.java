package com.example.demo;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@Configuration
@EnableKafka
public class DemoApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(DemoApplication.class);




	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}





	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(
				ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
				"localhost:9092");
		props.put(
				ConsumerConfig.GROUP_ID_CONFIG,
				"test");
		props.put(
				ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class);
		props.put(
				ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class);

		props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "PLAINTEXT");


		return new DefaultKafkaConsumerFactory<>(props);
	}





	@Bean
	public ProducerFactory<String, String> producerFactory() {

		Map<String, Object> props = new HashMap<>();
		props.put("key.serializer",
				"org.apache.kafka.common.serialization.StringSerializer");

		props.put("value.serializer",
				"org.apache.kafka.common.serialization.StringSerializer");

		props.put(
				ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
				"localhost:9092");
		props.put(
				ConsumerConfig.GROUP_ID_CONFIG,
				"test");
		props.put(
				ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class);
		props.put(
				ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class);

		props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "PLAINTEXT");


		return new DefaultKafkaProducerFactory<>(props);
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory) {
		KafkaTemplate<String, String> stringStringKafkaTemplate = new KafkaTemplate<>(producerFactory);
		stringStringKafkaTemplate.setObservationEnabled(true);//trying out with
		return stringStringKafkaTemplate;
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String>
	kafkaListenerContainerFactory(ConsumerFactory<String, String> consumerFactory) {


		ConcurrentKafkaListenerContainerFactory<String, String> factory =
				new ConcurrentKafkaListenerContainerFactory<>();

		//The following code enable observation in the consumer listener
		factory.getContainerProperties().setObservationEnabled(true);
		factory.setConsumerFactory(consumerFactory);


		return factory;
	}

}
