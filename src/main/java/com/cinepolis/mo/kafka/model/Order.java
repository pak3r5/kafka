package com.cinepolis.mo.kafka.model;

import lombok.Getter;
import lombok.Setter;


public class Order {
    private long order;
    private String description;
    public Order(long order, String description){
        this.order=order;
        this.description=description;
    }

    public Order() {
    }

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Order[order='"+this.order+"',description='"+this.description+"']";
    }
}
