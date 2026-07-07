package com.worldcupticket.ms_notifications.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String ORDER_CONFIRMED_QUEUE = "order.confirmed.queue";
    public static final String ORDER_CONFIRMED_EXCHANGE = "order.confirmed.exchange";
    public static final String ORDER_CONFIRMED_ROUTING_KEY = "order.confirmed";

    @Bean
    public Queue orderConfirmedQueue() {
        return new Queue(ORDER_CONFIRMED_QUEUE, true);
    }

    @Bean
    public TopicExchange orderConfirmedExchange() {
        return new TopicExchange(ORDER_CONFIRMED_EXCHANGE, true, false);
    }

    @Bean
    public Binding orderConfirmedBinding(Queue orderConfirmedQueue, TopicExchange orderConfirmedExchange) {
        return BindingBuilder.bind(orderConfirmedQueue)
                .to(orderConfirmedExchange)
                .with(ORDER_CONFIRMED_ROUTING_KEY);
    }
}
