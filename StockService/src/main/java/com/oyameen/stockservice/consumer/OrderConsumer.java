package com.oyameen.stockservice.consumer;

import com.oyameen.commonservice.modle.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderConsumer {

    @RabbitListener(queues = "StockQueue")
    public void consume(OrderEvent orderEvent) {
        log.info(String.format("Order event message received from Stock service = [ %s", orderEvent.toString() + " ]."));
    }
}
