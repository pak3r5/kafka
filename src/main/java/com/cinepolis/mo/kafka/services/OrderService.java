package com.cinepolis.mo.kafka.services;

import com.cinepolis.mo.kafka.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.IOException;

@Service
public class OrderService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;

    public ListenableFuture<SendResult<String, Order>> sendMessage(String topic, Order order) {
        logger.info(String.format("#### -> Producing new order -> %s", order));
        return this.kafkaTemplate.send(topic, order);
    }

    @Scheduled(fixedDelay= 10000)
    public void getOrderJob() throws IOException {
        logger.info("generate fake order event");
        Order order = new Order((long)(Math.random()*((100000-1)+1))+1,"pruebas");
        sendMessage("Order", order);
    }
}
