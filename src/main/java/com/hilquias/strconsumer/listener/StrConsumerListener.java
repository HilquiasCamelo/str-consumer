package com.hilquias.strconsumer.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
@Log4j2
@RequiredArgsConstructor
public class StrConsumerListener {

    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    /**
     * Método para ouvir mensagens do tópico "str-topic" no Kafka.
     *
     * @param kafkaMessage a mensagem recebida do Kafka
     */
    @KafkaListener(groupId = "group-01", topics = "str-topic", containerFactory = "strContainerFactory")
    public void listener(String kafkaMessage) {
        try {
            // Converter a mensagem em um nó JSON
            JsonNode jsonNode = objectMapper.readTree(kafkaMessage);

            // Obter a representação formatada da mensagem
            String formattedMessage = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(jsonNode);

            log.info("Received message:\n{}", formattedMessage);

            // Enviar a mensagem para o RabbitMQ usando o RabbitTemplate
            rabbitTemplate.convertAndSend("nome_da_fila", formattedMessage);

            log.info("Message sent to RabbitMQ: {}", formattedMessage);
            log.info("Message processed successfully!");

        } catch (IOException e) {
            log.error("Error processing message due to I/O exception: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage());
        }
    }
}
