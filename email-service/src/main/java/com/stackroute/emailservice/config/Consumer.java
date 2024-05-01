package com.stackroute.emailservice.config;

import com.stackroute.emailservice.AppConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Consumer {

    @Bean
    public Queue emailQueue() {
        return new Queue(AppConstants.EMAIL_QUEUE);
    }

    @Bean
    public Queue bookingEmailQueue() {
        return new Queue(AppConstants.BOOKING_EMAIL_QUEUE);
    }

    @Bean
    public Queue otpQueue() {
        return new Queue(AppConstants.OTP_QUEUE);
    }

    @Bean
    public Queue statusQueue() {
        return new Queue(AppConstants.STATUS_QUEUE);
    }

    @Bean
    public TopicExchange emailExchange() {
        return new TopicExchange(AppConstants.EXCHANGE);
    }

    @Bean
    public TopicExchange bookingEmailExchange() {
        return new TopicExchange(AppConstants.BOOKING_EXCHANGE);
    }

    @Bean
    public Binding emailBinding(Queue emailQueue, TopicExchange emailExchange) {
        return BindingBuilder.bind(emailQueue()).to(emailExchange()).with(AppConstants.EMAIL_QUEUE_ROUTING_KEY);
    }

    @Bean
    public Binding bookingBinding() {
        return BindingBuilder.bind(bookingEmailQueue())
                .to(bookingEmailExchange())
                .with(AppConstants.BOOKING_QUEUE_ROUTING_KEY);
    }

    @Bean
    public Binding otpBinding(Queue otpQueue, TopicExchange emailExchange) {
        return BindingBuilder.bind(otpQueue())
                .to(emailExchange())
                .with(AppConstants.OTP_QUEUE_ROUTING_KEY);
    }

    @Bean
    public Binding statusBinding() {
        return BindingBuilder.bind(statusQueue())
                .to(bookingEmailExchange())
                .with(AppConstants.STATUS_KEY);
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
