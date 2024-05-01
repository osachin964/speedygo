package com.stackroute.bookingservice.config;
import com.stackroute.bookingservice.util.AppConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookingConfig {
    @Bean
    public Queue emailQueue() {
        return new Queue(AppConstants.EMAIL_QUEUE);
    }
    @Bean
    public Queue statusQueue(){
        return new Queue(AppConstants.STATUS_QUEUE);
    }

    @Bean
    public TopicExchange emailExchange() {
        return new TopicExchange(AppConstants.EXCHANGE);
    }

    @Bean
    public Binding emailBinding(Queue emailQueue, TopicExchange emailExchange) {
        return BindingBuilder.bind(emailQueue()).to(emailExchange()).with(AppConstants.EMAIL_QUEUE_ROUTING_KEY);
    }
    @Bean
    public Binding statusBinding(Queue statusQueue, TopicExchange emailExchange){
        return BindingBuilder.bind(statusQueue()).to(emailExchange()).with(AppConstants.STATUS_KEY);
    }
    @Bean
    public Queue reviewQueue() {
        return new Queue(AppConstants.REVIEW_QUEUE);
    }

    @Bean
    public TopicExchange reviewExchange() {
        return new TopicExchange(AppConstants.REVIEW_EXCHANGE);
    }

    @Bean
    public Binding reviewBinding(Queue reviewQueue, TopicExchange reviewExchange) {
        return BindingBuilder.bind(reviewQueue()).to(reviewExchange()).with(AppConstants.REVIEW_QUEUE_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate userTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
