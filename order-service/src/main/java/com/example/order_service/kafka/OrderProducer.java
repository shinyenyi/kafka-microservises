package com.example.order_service.kafka;

import com.example.base_domains.dto.OrderEvents;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);

    private NewTopic topic;

    private KafkaTemplate<String, OrderEvents> kafkaTemplate;

    public OrderProducer(NewTopic topic, KafkaTemplate<String, OrderEvents> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(OrderEvents orderEvents) {
        LOGGER.info("Order event sent -> {}", orderEvents.toString());

        //create message
        Message<OrderEvents> message = MessageBuilder.withPayload(orderEvents)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();

        kafkaTemplate.send(message);
    }
}
