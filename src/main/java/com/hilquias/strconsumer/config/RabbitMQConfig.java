package com.hilquias.strconsumer.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // Configurações adicionais, se necessário
        return rabbitTemplate;
    }

    @Bean
    public Queue queue() {
        // Criar a fila com os parâmetros desejados, incluindo a propriedade durable como true
        return new Queue("nome_da_fila", true, false, false);
    }

}