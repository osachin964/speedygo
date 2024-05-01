package com.stackroute.transporterservice.config;

import com.stackroute.transporterservice.util.AppConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TransporterConsumer {


    @Bean
    public Queue transporterQueue() {
        return new Queue(AppConstants.TRANSPORTER_QUEUE);
    }

    @Bean
    public TopicExchange transporterExchange() {
        return new TopicExchange(AppConstants.EXCHANGE);
    }

    @Bean
    public Binding transporterBinding(Queue transporterQueue, TopicExchange transporterExchange) {
        return BindingBuilder.bind(transporterQueue).to(transporterExchange).with(AppConstants.TRANSPORTER_QUEUE_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

}