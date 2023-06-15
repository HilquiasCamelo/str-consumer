package com.hilquias.strconsumer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public void saveMessage(String topic, String message) {
        try {
            // Converter a mensagem em um nó JSON
            JsonNode jsonNode = objectMapper.readTree(message);

            // Obter a representação formatada da mensagem
            String formattedMessage = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);

            // Gerar um identificador único para a chave
            String key = topic + "-" + UUID.randomUUID().toString();

            // Salvar a mensagem no Redis usando a chave única
            redisTemplate.opsForValue().set(key, formattedMessage);

            log.info("Mensagem Kafka salva no Redis: " + formattedMessage);
        } catch (Exception e) {
            log.info("Error processing message: " + e.getMessage());
        }
    }
}
