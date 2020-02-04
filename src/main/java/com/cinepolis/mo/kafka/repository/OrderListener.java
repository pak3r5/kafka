package com.cinepolis.mo.kafka.repository;

import com.cinepolis.mo.kafka.model.Order;

public interface OrderListener {
    void onData(Order order);
    void processComplete();
}
