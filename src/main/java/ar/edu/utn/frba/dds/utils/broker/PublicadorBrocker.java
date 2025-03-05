package ar.edu.utn.frba.dds.utils.broker;

import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
import static java.nio.charset.StandardCharsets.UTF_8;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;

public class PublicadorBrocker {

  private static String host = "<Host>";
  private static String username = "<User>";
  private static String password = "<Password>";


  public static void main(String[] args) {
    final String host =  "<Host>";
    final String username = "<User>";
    final String password = "<Password>";

    // create an MQTT client
    final Mqtt5BlockingClient client = MqttClient.builder()
        .useMqttVersion5()
        .serverHost(host)
        .serverPort(8883)
        .sslWithDefaultConfig()
        .buildBlocking();

    // connect to HiveMQ Cloud with TLS and username/pw
    client.connectWith()
        .simpleAuth()
        .username(username)
        .password(UTF_8.encode(password))
        .applySimpleAuth()
        .send();

    System.out.println("Connected successfully");

    // subscribe to the topic "my/test/topic"
//    client.subscribeWith()
//        .topicFilter("aperturasHeladera")
//        .send();




    //set a callback that is called when a message is received (using the async API style)
//    client.toAsync().publishes(ALL, publish -> {
//      System.out.println("Received message: " +
//          publish.getTopic() + " -> " +
//          UTF_8.decode(publish.getPayload().get()));
//
//    // disconnect the client after a message was received
//      client.disconnect();
//    });


    // publish a message to the topic "my/test/topic"
    client.publishWith()
        .topic("aperturasHeladera")
        .payload(UTF_8.encode("Hello"))
        .send();
    client.disconnect();

  }

  public static void publicar (String topic, String message){
    Mqtt5BlockingClient client = MqttClient.builder()
        .useMqttVersion5()
        .serverHost(host)
        .serverPort(8883)
        .sslWithDefaultConfig()
        .buildBlocking();

    client.connectWith()
        .simpleAuth()
        .username(username)
        .password(UTF_8.encode(password))
        .applySimpleAuth()
        .send();

    client.publishWith()
        .topic(topic)
        .payload(UTF_8.encode(message))
        .send();
    client.disconnect();
  }


}
