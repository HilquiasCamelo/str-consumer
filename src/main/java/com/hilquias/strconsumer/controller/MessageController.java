package com.hilquias.strconsumer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final RabbitTemplate rabbitTemplate;

    @PostMapping("/messages")
    public void receiveMessage(@RequestBody String message) {
        // Processar a mensagem recebida e, em seguida, enviar para o RabbitMQ
        rabbitTemplate.convertAndSend("nome_da_fila", message);
    }
}
