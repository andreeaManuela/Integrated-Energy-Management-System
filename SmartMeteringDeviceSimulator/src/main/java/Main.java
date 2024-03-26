import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {
    private final static String TOPIC_DEVICEID="deviceID_topic";
    private final static String QUEUE_NAME= "MessageProducer";
    private final static String ROUTING_KEY="device.measurement.energy";
    private static BufferedReader br;
    private static List<String> deviceIds;
    private static int currentDeviceIndex = 0;

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory= new ConnectionFactory();
        factory.setUri("amqps://zildfpbx:J2BlzGnevi-j7e8LdIDgMc7poyB5Nlpn@sparrow.rmq.cloudamqp.com/zildfpbx");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //declare the topic
        channel.exchangeDeclare(TOPIC_DEVICEID, "topic", true);

        String configContent = new String(Files.readAllBytes(Paths.get("config.json")));
        JSONArray devicesArray = new JSONObject(configContent).getJSONArray("devices");
        List<String> deviceIds = new ArrayList<>();
        for (int i = 0; i < devicesArray.length(); i++) {
            JSONObject deviceObj = devicesArray.getJSONObject(i);
            deviceIds.add(deviceObj.getString("device_id"));
        }

        InputStream is = Main.class.getClassLoader().getResourceAsStream("sensor.csv");
        br = new BufferedReader(new InputStreamReader(is));

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            try {
                String deviceId = deviceIds.get(currentDeviceIndex);
                sendDataToBroker(channel, deviceId);
                currentDeviceIndex = (currentDeviceIndex + 1) % deviceIds.size();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, 7, TimeUnit.SECONDS);
    }

    public static void sendDataToBroker(Channel channel, String device_id) throws IOException {
        String line; ;
            while((line= br.readLine()) !=null){
                String[] values = line.split(",");
                String measurement_value = values[0];
                long timestamp = System.currentTimeMillis();

                //cream un obiect json
                JSONObject messageObj= new JSONObject()
;
                messageObj.put("timestamp", timestamp);
                messageObj.put("device_id", device_id);
                messageObj.put("measurement_value", measurement_value);

                String message = messageObj.toString();
                //channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
                channel.basicPublish(TOPIC_DEVICEID, ROUTING_KEY, null, message.getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + message + "'");
                break;
            }
            if(line == null){
                br.close();
            }
    }


}
