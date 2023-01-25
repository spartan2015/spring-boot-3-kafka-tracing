package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.Properties;

public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {

        ObjectMapper objectMapper = new ObjectMapper();
        final KafkaProducer<String, String> producer;
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("enable.idempotence", true);
        props.put("max.in.flight.requests.per.connection", 1);
        props.put("client-id", "tester");
        props.put("producer-id", "tester");
        props.put("transactional.id", "my-transactional-id");
        props.put("tester." + ProducerConfig.MAX_BLOCK_MS_CONFIG, 60000);
        props.put("max.block.ms", 60000);
        producer = new KafkaProducer<>(props);
//        producer.initTransactions();

        producer.initTransactions();

        int i = 0;
        while (i<1) {

            producer.beginTransaction();
            producer.send(new ProducerRecord<>("input", "1","test message"));
            producer.commitTransaction();

            i++;
            Thread.sleep(10);
        }
    }
}
