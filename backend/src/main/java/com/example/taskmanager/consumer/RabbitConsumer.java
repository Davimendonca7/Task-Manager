package com.example.taskmanager.consumer;

import com.example.taskmanager.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitConsumer {

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void receiveMessage(String message) {
        System.out.println("📩 Mensagem recebida da fila: " + message);
    }
}
