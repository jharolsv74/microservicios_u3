package com.tuempresa.turnos.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Configuration
public class RabbitMQConfig {
    public static final String CITA_CONFIRMADA_QUEUE = "citas.confirmadas";
    public static final String CITA_CONFIRMADA_EXCHANGE = "citas.confirmadas.exchange";
    public static final String CITA_CONFIRMADA_ROUTING_KEY = "citas.confirmadas";

    @Bean
    public Queue citaConfirmadaQueue() {
        return new Queue(CITA_CONFIRMADA_QUEUE, true);
    }

    @Bean
    public DirectExchange citaConfirmadaExchange() {
        return new DirectExchange(CITA_CONFIRMADA_EXCHANGE, true, false);
    }

    @Bean
    public Binding citaConfirmadaBinding(Queue citaConfirmadaQueue, DirectExchange citaConfirmadaExchange) {
        return BindingBuilder.bind(citaConfirmadaQueue).to(citaConfirmadaExchange).with(CITA_CONFIRMADA_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jackson2JsonMessageConverter());
        return template;
    }
}
