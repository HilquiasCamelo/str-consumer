package com.hilquias.strconsumer.listener;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hilquias.strconsumer.service.RedisService;

@Component
@Log4j2
public class StrConsumerListener {
    private final RedisService redisService;
    private final ObjectMapper objectMapper;

    public StrConsumerListener(RedisService redisService, ObjectMapper objectMapper) {
        this.redisService = redisService;
        this.objectMapper = objectMapper;
    }

    /**
     * Método para ouvir mensagens do tópico "str-topic" no Kafka.
     *
     * @param message a mensagem recebida do Kafka
     */
    @KafkaListener(groupId = "group-01", topics = "str-topic", containerFactory = "strContainerFactory")
    public void listener(String message) {
        try {
            // Converter a mensagem em um nó JSON
            JsonNode jsonNode = objectMapper.readTree(message);

            // Obter a representação formatada da mensagem
            String formattedMessage = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);

            log.info("Received message:\n{}", formattedMessage);

            log.info("Message processed successfully!");

            redisService.saveMessage("str-topic", message); // Altere o nome do tópico conforme necessário
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage());
        }
    }
}
