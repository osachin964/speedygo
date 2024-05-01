package com.stackroute.reviewservice.config;

import com.stackroute.reviewservice.util.AppConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ReviewConsumer {


    @Bean
    public Queue reviewQueue() {
        return new Queue(AppConstants.REVIEW_QUEUE);
    }

    @Bean
    public TopicExchange reviewExchange() {
        return new TopicExchange(AppConstants.REVIEW_EXCHANGE);
    }

    @Bean
    public Binding reviewBinding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(AppConstants.REVIEW_QUEUE_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate bookingTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

}
