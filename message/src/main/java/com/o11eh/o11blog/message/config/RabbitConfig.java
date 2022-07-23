package com.o11eh.o11blog.message.config;

import com.o11eh.o11blog.servicebase.constants.RabbitConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Exchange defaultExchange() {
        return ExchangeBuilder.directExchange(RabbitConstants.EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue emailQueue() {
        return QueueBuilder.durable(RabbitConstants.QUEUE_EMAIL).ttl(60000).build();
    }

    @Bean
    public Binding emailBinding(Exchange exchange, Queue emailQueue) {
        return BindingBuilder.bind(emailQueue).to(exchange).with(RabbitConstants.ROUTING_KEY_EMAIL).noargs();
    }


    @Bean
    public Queue articleQueue() {
        return QueueBuilder.durable(RabbitConstants.QUEUE_ARTICLE).build();
    }

    @Bean
    public Binding articleBinding(Exchange exchange, Queue articleQueue) {
        return BindingBuilder.bind(articleQueue).to(exchange).with(RabbitConstants.ROUTING_KEY_ARTICLE).noargs();
    }
}
