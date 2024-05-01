package com.stackroute.registrationservice.configuration;


import com.stackroute.registrationservice.utils.AppConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {



    @Bean
    public Queue registerQueue() {
        return new Queue(AppConstants.USERS_QUEUE);
    }
    @Bean
    public Queue customerQueue() {
        return new Queue(AppConstants.CUSTOMER_QUEUE);
    }

    @Bean
    public Queue transporterQueue() {
        return new Queue(AppConstants.TRANSPORTER_QUEUE );
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(AppConstants.EMAIL_QUEUE);
    }

    @Bean
    public Queue otpQueue() {
        return new Queue(AppConstants.OTP_QUEUE);
    }

    @Bean
    public Queue passwordQueue() {
        return new Queue(AppConstants.PASSWORD_QUEUE );
    }


    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(AppConstants.EXCHANGE);
    }



    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    Binding userBinding(Queue registerQueue, TopicExchange exchange) {
        return BindingBuilder.bind(registerQueue()).to(exchange).with(AppConstants.USER_QUEUE_ROUTING_KEY);
    }

    @Bean
    Binding customerBinding(Queue customerQueue, TopicExchange exchange) {
        return BindingBuilder.bind(customerQueue()).to(exchange).with(AppConstants.CUSTOMER_QUEUE_ROUTING_KEY);
    }


    @Bean
    Binding emailBinding(Queue emailQueue, TopicExchange exchange) {
        return BindingBuilder.bind(emailQueue()).to(exchange).with(AppConstants.EMAIL_QUEUE_ROUTING_KEY);
    }
    @Bean
    Binding otpQueue(Queue otpQueue, TopicExchange exchange) {
        return BindingBuilder.bind(otpQueue()).to(exchange).with(AppConstants.OTP_QUEUE_ROUTING_KEY);
    }

    @Bean
    Binding passwordQueue(Queue passwordQueue, TopicExchange exchange) {
        return BindingBuilder.bind(passwordQueue()).to(exchange).with(AppConstants.PASSWORD_QUEUE_ROUTING_KEY);
    }
    @Bean
    public Binding binding(Queue transporterQueue, TopicExchange exchange) {
        return BindingBuilder.bind(transporterQueue()).to(exchange).with(AppConstants.TRANSPORTER_QUEUE_ROUTING_KEY);
    }
    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

}
