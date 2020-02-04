package com.cinepolis.mo.kafka.controller;

import com.cinepolis.mo.kafka.Processor.OrderProcessor;
import com.cinepolis.mo.kafka.model.Order;
import com.cinepolis.mo.kafka.repository.OrderListener;
import com.cinepolis.mo.kafka.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private OrderProcessor orderProcessor;

    @Autowired
    private OrderService orderService;

    private Flux<Order> bridge;

    public OrderController(){
        this.bridge =createBridge().publish().autoConnect().cache(10).log();
    }

    @GetMapping(value = "/order", produces = "text/event-stream;charset=UTF-8")
    public Flux<Order> getOrder() {
        /*try {
            logger.info("try");
            this.orderService.getOrderJob();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }*/
        return bridge;
    }

    private Flux<Order> createBridge() {
        Flux<Order> bridge = Flux.create(sink -> { // (2)
            orderProcessor.register(new OrderListener() {

                @Override
                public void processComplete() {
                    sink.complete();
                }

                @Override
                public void onData(Order data) {
                    sink.next(data);
                }
            });
        });
        return bridge;
    }
}
