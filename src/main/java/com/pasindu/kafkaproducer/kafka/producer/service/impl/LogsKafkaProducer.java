package com.pasindu.kafkaproducer.kafka.producer.service.impl;

import com.pasindu.kafkaproducer.kafka.model.LogAvroModel;
import com.pasindu.kafkaproducer.kafka.producer.service.KafkaProducer;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.PreDestroy;

@Service
public class LogsKafkaProducer implements KafkaProducer<Long, LogAvroModel> {

    private static final Logger LOG = LoggerFactory.getLogger(LogsKafkaProducer.class);

    private KafkaTemplate<Long, LogAvroModel> kafkaTemplate;

    public LogsKafkaProducer(KafkaTemplate<Long, LogAvroModel> template) {
        this.kafkaTemplate = template;
    }

    /**
     * Write as Async
     * @param topicName
     * @param key
     * @param message
     */
    @Override
    public void send(String topicName, Long key, LogAvroModel message) {
        LOG.info("Sending message='{}' to topic='{}'", message, topicName);
        ListenableFuture<SendResult<Long, LogAvroModel>> kafkaResultFuture =
                kafkaTemplate.send(topicName, key, message);
        addCallback(topicName, message, kafkaResultFuture);
    }

    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            LOG.info("Closing kafka producer!");
            kafkaTemplate.destroy();
        }
    }

    private void addCallback(String topicName, LogAvroModel message,
                             ListenableFuture<SendResult<Long, LogAvroModel>> kafkaResultFuture) {
        kafkaResultFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable throwable) {
                LOG.error("Error while sending message {} to topic {}", message.toString(), topicName, throwable);
            }

            @Override
            public void onSuccess(SendResult<Long, LogAvroModel> result) {
                RecordMetadata metadata = result.getRecordMetadata();
                LOG.debug("Received new metadata. Topic: {}; Partition {}; Offset {}; Timestamp {}, at time {}",
                        metadata.topic(),
                        metadata.partition(),
                        metadata.offset(),
                        metadata.timestamp(),
                        System.nanoTime());
            }
        });
    }
}
