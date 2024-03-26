package com.example.backendcommunicationmonitoring.MessageConsumer;

import com.example.backendcommunicationmonitoring.entities.DeviceIdTable;
import com.example.backendcommunicationmonitoring.entities.Measurement;
import com.example.backendcommunicationmonitoring.services.MeasurementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Consumer {
    private final static String QUEUE_NAME="MessageProducer";
    private final static String TOPIC_DEVICEID="deviceID_topic";
    private final static String ROUTING_KEY="device.measurement.*";
    private MeasurementService measurementService;
    private final Map<UUID, Double> deviceSums = new HashMap<>();
    private final Set<UUID> stoppedDevices = new HashSet<>();

    @Autowired
    public Consumer(MeasurementService measurementService){
        this.measurementService=measurementService;
    }

    // queues = Consumer.QUEUE_NAME
    @RabbitListener(
            bindings = @QueueBinding(
                    value= @Queue(value = QUEUE_NAME, durable = "true"),
                    exchange = @Exchange(value = TOPIC_DEVICEID, type = "topic", durable = "true"),
                    key = ROUTING_KEY
            ))

    public void reciveMessage(String message){
        JSONObject json=new JSONObject(message);
        System.out.println(" [x] Received '" + message + "'");
        long timestamp = json.getLong("timestamp");
        UUID device_id = UUID.fromString(json.getString("device_id"));
        String measurement_value = json.getString("measurement_value");

        if (stoppedDevices.contains(device_id)) {
            return;
        }

        DeviceIdTable id_device = new DeviceIdTable(device_id);
        Measurement measure=new Measurement(timestamp, id_device , Double.parseDouble(measurement_value));

        measurementService.insert(measure);

        deviceSums.merge(device_id, Double.parseDouble(measurement_value), Double::sum);
        double limita_deviceID= measurementService.getLimitaDeviceId(device_id);

        if (deviceSums.get(device_id) >= limita_deviceID) {
            measurementService.notify(device_id, deviceSums.get(device_id), limita_deviceID);
            deviceSums.put(device_id, 0.0);
            stoppedDevices.add(device_id);
            System.out.println("ATENTIE!!!");
            System.out.println(deviceSums);
        }

    }

}


