package com.oyameen.smsservice.consumer;

import com.oyameen.commonservice.modle.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderConsumer {

    @RabbitListener(queues = "SMSQueue")
    public void consume(OrderEvent orderEvent) {
        log.info(String.format("Order event message received from SMS service = [ %s", orderEvent.toString() + " ]."));
    }
}
