package com.oyameen.orderservice.controller;

import com.oyameen.commonservice.modle.ExchangeType;
import com.oyameen.commonservice.modle.Order;
import com.oyameen.commonservice.modle.OrderEvent;
import com.oyameen.commonservice.modle.OrderEventStatus;
import com.oyameen.orderservice.producer.OrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class OrderController {


    @Autowired
    private OrderProducer orderProducer;

    @PostMapping("/orders")
    public ResponseEntity<String> publishOrder(@RequestBody Order order,
                                               @RequestParam(value = "exchangeType") ExchangeType exchangeType) {
        if (order == null || exchangeType == null) {
            return ResponseEntity.badRequest().body("Invalid request provided, please ensure to provide order and exchangeType.");
        }

        order.setId(UUID.randomUUID().toString());
        OrderEvent orderEvent = new OrderEvent(order, "order still in pending state", OrderEventStatus.PENDING);
        switch (exchangeType) {
            case DIRECT -> orderProducer.sendMessageByDirectExchange(orderEvent);
            case FAN_OUT -> orderProducer.sendMessageByFanOutExchange(orderEvent);
            case TOPIC -> orderProducer.sendMessageByTopicExchange(orderEvent);
            case HEADERS -> orderProducer.sendMessageByHeaderExchange(orderEvent);
        }

        return ResponseEntity.ok("Adding order to RabbitMQ done successfully.");
    }
}
