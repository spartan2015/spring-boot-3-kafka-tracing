package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransactionalService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @Transactional
    public void doService(){
        kafkaTemplate.send("output","test");
    }
}
