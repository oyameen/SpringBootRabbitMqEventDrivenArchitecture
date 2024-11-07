package com.oyameen.smsservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue stockQueue() {
        return new Queue("StockQueue");
    }

    @Bean
    public Queue smsQueue() {
        return new Queue("SMSQueue");
    }

    @Bean
    public Queue emailQueue() {
        return new Queue("EmailQueue");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }


    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fan_outExchange");
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange("headersExchange");
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Binding directStockBinding() {
        return BindingBuilder.bind(stockQueue()).to(directExchange()).with("stock");
    }

    @Bean
    public Binding directSMSBinding() {
        return BindingBuilder.bind(smsQueue()).to(directExchange()).with("sms");
    }


    @Bean
    public Binding fanoutStockBinding() {
        return BindingBuilder.bind(stockQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutSMSBinding() {
        return BindingBuilder.bind(smsQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding topicStockBinding() {
        return BindingBuilder.bind(stockQueue()).to(topicExchange()).with("*.stock.*");
    }

    @Bean
    public Binding topicSMSBinding() {
        return BindingBuilder.bind(smsQueue()).to(topicExchange()).with("*.sms.*");
    }

    @Bean
    public Binding topicEmailBinding() {
        return BindingBuilder.bind(emailQueue()).to(topicExchange()).with("#.email");
    }

    @Bean
    public Binding headersStockBinding() {
        Map<String, Object> map = new HashMap<>();
        map.put("value1", "stock");
        map.put("value2", "sms");
        return BindingBuilder.bind(stockQueue()).to(headersExchange()).whereAny(map).match();
    }

    @Bean
    public Binding headersSMSBinding() {
        Map<String, Object> map = new HashMap<>();
        map.put("value1", "xyz");
        map.put("value2", "sms");
        return BindingBuilder.bind(smsQueue()).to(headersExchange()).whereAny(map).match();
    }

    @Bean
    public Binding headersEmailBinding() {
        Map<String, Object> map = new HashMap<>();
        map.put("value1", "stock");
        map.put("value2", "email");
        return BindingBuilder.bind(emailQueue()).to(headersExchange()).whereAll(map).match();
    }
}
