package com.cinepolis.mo.kafka.Processor;

import com.cinepolis.mo.kafka.model.Order;
import com.cinepolis.mo.kafka.repository.OrderListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OrderProcessor {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private OrderListener orderListener;

    public void register(OrderListener orderListener) {
        this.orderListener = orderListener;
    }

    public void onEvent(Order event) {
        if (orderListener != null) {
            orderListener.onData(event);
        }
    }

    public void onComplete() {
        if (orderListener != null) {
            orderListener.processComplete();
        }
    }

    @KafkaListener(topics = "order", groupId = "cnplsMoGroup")
    public void consume(Order order) throws IOException {
        logger.info(String.format("#### -> consumed new order -> %s", order));
        onEvent(order);
    }
}
