package com.example.sd_frateanandreea_backend2.RabbitMQ;

import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeviceEventListener {
    private final RabbitTemplate rabbitTemplate;
    private static final String ROUTING_KEY_INSERTED = "device.inserted";
    private static final String ROUTING_KEY_DELETED = "device.deleted";
    private static final String ROUTING_KEY_UPDATE = "device.update";
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceEventListener.class);
    private String exchangeTopic;

    @Autowired
    public DeviceEventListener(RabbitTemplate rabbitTemplate, @Value("${rabbitmq.exchange.name}") String exchangeTopic){
        this.rabbitTemplate=rabbitTemplate;
        this.exchangeTopic=exchangeTopic;
    }

    public void sendMessageInserted(String eventType, UUID deviceId, String max_consump) {
        String message = eventType + ":" + deviceId+":"+max_consump;
        String routingKey= ROUTING_KEY_INSERTED;;

        rabbitTemplate.convertAndSend(exchangeTopic, routingKey, message);
        LOGGER.info("Sent {} event for device ID: {} with routing key: {}", eventType, deviceId, routingKey, max_consump);
    }

    public void sendMessageUpdate(String eventType, UUID deviceId, String max_consump) {
        String message = eventType + ":" + deviceId.toString() + ":" + max_consump;
        String routingKey= ROUTING_KEY_UPDATE;;

        rabbitTemplate.convertAndSend(exchangeTopic, routingKey, message);
        LOGGER.info("Sent {} event for device ID: {} with routing key: {} and max consumption: {}", eventType, deviceId, routingKey, max_consump);
    }

    public void sendMessageDelete(String eventType, UUID deviceId) {
        System.out.println(deviceId);
        String message = eventType + ":" + deviceId;
        String routingKey = ROUTING_KEY_DELETED;

        rabbitTemplate.convertAndSend(exchangeTopic, routingKey, message);
        LOGGER.info("Sent {} event for device ID: {} with routing key: {}", eventType, deviceId, routingKey);
    }

}
