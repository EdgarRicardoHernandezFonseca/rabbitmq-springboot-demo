package com.edgar.rabbitmq.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EMAIL_QUEUE = "email.queue";

    public static final String AUDIT_QUEUE = "audit.queue";
    
    public static final String RETRY_QUEUE = "retry.queue";

    public static final String FANOUT_EXCHANGE =
            "order.fanout.exchange";
    
    public static final String DLQ =
            "dead.letter.queue";

    public static final String DLX =
            "dead.letter.exchange";

    @Bean
    public Queue emailQueue() {

        return QueueBuilder
                .durable(EMAIL_QUEUE)
                .withArgument(
                        "x-dead-letter-exchange",
                        DLX
                )
                .build();
    }

    @Bean
    public Queue auditQueue() {
        return new Queue(AUDIT_QUEUE);
    }
    
    @Bean
    Queue retryQueue() {

        Map<String, Object> args =
                new HashMap<>();

        args.put(
                "x-message-ttl",
                5000
        );

        args.put(
                "x-dead-letter-exchange",
                FANOUT_EXCHANGE
        );

        return new Queue(
                RETRY_QUEUE,
                true,
                false,
                false,
                args
        );
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(
                FANOUT_EXCHANGE
        );
    }

    @Bean
    public Binding emailBinding(
            Queue emailQueue,
            FanoutExchange fanoutExchange) {

        return BindingBuilder
                .bind(emailQueue)
                .to(fanoutExchange);
    }

    @Bean
    public Binding auditBinding(
            Queue auditQueue,
            FanoutExchange fanoutExchange) {

        return BindingBuilder
                .bind(auditQueue)
                .to(fanoutExchange);
    }
     
    @Bean
    public FanoutExchange deadLetterExchange() {

        return new FanoutExchange(DLX);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory) {

        RabbitTemplate template =
                new RabbitTemplate(connectionFactory);

        template.setMessageConverter(
                messageConverter());

        return template;
    }
    
    @Bean
    public Queue deadLetterQueue() {

        return new Queue(DLQ);
    }
    
    @Bean
    public Binding deadLetterBinding(
            Queue deadLetterQueue,
            FanoutExchange deadLetterExchange) {

        return BindingBuilder
                .bind(deadLetterQueue)
                .to(deadLetterExchange);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory
    rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter converter) {

        SimpleRabbitListenerContainerFactory factory =
                new SimpleRabbitListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(converter);

        return factory;
    }
}