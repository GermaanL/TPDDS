package ar.edu.utn.frba.dds.utils.broker;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.logging.Level.ALL;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import okhttp3.Call;
import org.eclipse.paho.client.mqttv3.MqttClient;

@Getter @Setter
public class SuscriptorBrocker {

  private static String host = "<Host>";
  private static String username = "<User>";
  private static String password = "<Password>";
  private static CallbackExecution callbackExecution;

  public static void main(String[] args) throws Exception {

    final String host = "<Host>";
    final String username = "<User>";
    final String password = "<Password>";

    // create an MQTT client
    final Mqtt5BlockingClient client = Mqtt5Client.builder()
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
    client.toAsync().subscribeWith()
        .topicFilter("aperturasHeladera")
        .qos(MqttQos.AT_LEAST_ONCE)
        .callback(publish -> {
          System.out.println("Received message: " +
              publish.getTopic() + " -> " +
              UTF_8.decode(publish.getPayload().get()).toString());
        })
        .send();


     //set a callback that is called when a message is received (using the async API style)
//    client.toAsync().publishes(ALL, publish -> {
//      System.out.println("Received message: " +
//          publish.getTopic() + " -> " +
//          UTF_8.decode(publish.getPayload().get()));

      // disconnect the client after a message was received
      //client.disconnect();
//    });

//      client.toAsync().publishes(ALL, publish -> {
//        System.out.println("Received message: " +
//            publish.getTopic() + " -> " +
//            UTF_8.decode(publish.getPayload().get()));
//      });


    // publish a message to the topic "my/test/topic"
//    client.publishWith()
//        .topic("aperturasHeladera")
//        .payload(UTF_8.encode("Hello"))
//        .send();



  }

  public static void suscribirse(String topic, CallbackExecution callbackExecution) {
    Mqtt5BlockingClient client = Mqtt5Client.builder()
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

    System.out.println("Connected successfully to topic: " + topic);

    client.toAsync().subscribeWith()
        .topicFilter(topic)
        .qos(MqttQos.AT_LEAST_ONCE)
        .callback(publish -> {
          System.out.println("Received message: " +
              publish.getTopic() + " -> " +
              UTF_8.decode(publish.getPayload().get()));

          callbackExecution.execute(UTF_8.decode(publish.getPayload().get()).toString());
        })
        .send();

  }


}
