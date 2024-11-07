package com.oyameen.orderservice.producer;

import com.oyameen.commonservice.modle.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    MessageConverter messageConverter;

    public void sendMessageByDirectExchange(OrderEvent orderEvent) {
        log.warn("Order event sent to stock and sms service by direct exchange only.");
        log.info(String.format("Order event message by direct exchange sent = [ %s", orderEvent.toString() + " ]."));
        rabbitTemplate.convertAndSend("directExchange", "stock", orderEvent);
        rabbitTemplate.convertAndSend("directExchange", "sms", orderEvent);
    }

    public void sendMessageByFanOutExchange(OrderEvent orderEvent) {
        log.warn("Order event sent to stock and sms service by fan out exchange only.");
        log.info(String.format("Order event message by fan out exchange sent = [ %s", orderEvent.toString() + " ]."));
        rabbitTemplate.convertAndSend("fan_outExchange", "", orderEvent);
    }

    public void sendMessageByTopicExchange(OrderEvent orderEvent) {
        log.warn("Order event sent to sms and email service by topic exchange only.");
        log.info(String.format("Order event message by topic exchange sent = [ %s", orderEvent.toString() + " ]."));
        rabbitTemplate.convertAndSend("topicExchange", "stock.sms.email", orderEvent);
    }

    public void sendMessageByHeaderExchange(OrderEvent orderEvent) {
        log.warn("Order event sent to stock and sms service by header exchange only.");
        log.info(String.format("Order event message by headers exchange sent = [ %s", orderEvent.toString() + " ]."));
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("value1", "stock");
        messageProperties.setHeader("value2", "sms");
        Message message = messageConverter.toMessage(orderEvent, messageProperties);
        rabbitTemplate.convertAndSend("headersExchange", "", message);
    }
}
