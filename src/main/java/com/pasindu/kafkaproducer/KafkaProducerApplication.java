package com.pasindu.kafkaproducer;

import com.pasindu.kafkaproducer.kafka.admin.client.KafkaAdminClient;
import com.pasindu.kafkaproducer.kafka.config.data.KafkaConfigData;
import com.pasindu.kafkaproducer.kafka.model.LogAvroModel;
import com.pasindu.kafkaproducer.kafka.producer.service.KafkaProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableAsync
public class KafkaProducerApplication {

    private final KafkaAdminClient kafkaAdminClient;

    public KafkaProducerApplication(KafkaAdminClient kafkaAdminClient) {
        this.kafkaAdminClient = kafkaAdminClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerApplication.class, args);
    }

    @PostConstruct
    public void postConstruct() {
        kafkaAdminClient.createTopics();
        kafkaAdminClient.checkSchemaRegistry();
    }

}
