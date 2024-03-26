package com.example.backendcommunicationmonitoring.MessageConsumer;

import com.example.backendcommunicationmonitoring.entities.DeviceIdTable;
import com.example.backendcommunicationmonitoring.entities.Measurement;
import com.example.backendcommunicationmonitoring.services.DeviceIdTabelService;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ConsumerDevice {
    private final static String QUEUE_NAME="DeviceChanges";
    private final static String TOPIC_DEVICEID="deviceStatus";
    private final static String ROUTING_KEY="device.*";
    private DeviceIdTabelService deviceIdTabelService;

    @Autowired
    public ConsumerDevice(DeviceIdTabelService deviceIdTabelService){
        this.deviceIdTabelService=deviceIdTabelService;
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value= @Queue(value = QUEUE_NAME, durable = "true"),
                    exchange = @Exchange(value = TOPIC_DEVICEID, type = "topic", durable = "true"),
                    key = ROUTING_KEY
            ))
    public void reciveMessage(String message){
        System.out.println("Received message: " + message);
        message = message.replace("\"", "");
        String[] parts = message.split(":");
        String eventType = parts[0];
        UUID device_id= UUID.fromString(parts[1]);
        String maxConsump = parts.length > 2 ? parts[2] : null;

        if(eventType.equals("inserted")) {
            deviceIdTabelService.insert(device_id, maxConsump);
        } else if ("deleted".equals(eventType)) {
            deviceIdTabelService.delete(device_id);
        } else if ("update".equals(eventType)) {
            deviceIdTabelService.updateDevice(device_id, maxConsump);
        }


    }
}
