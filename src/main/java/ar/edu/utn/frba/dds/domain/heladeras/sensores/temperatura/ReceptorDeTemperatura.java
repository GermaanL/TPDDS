package ar.edu.utn.frba.dds.domain.heladeras.sensores.temperatura;


import ar.edu.utn.frba.dds.server.controllers.HeladeraController;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import ar.edu.utn.frba.dds.utils.broker.CallbackExecution;
import ar.edu.utn.frba.dds.utils.broker.SuscriptorBrocker;
import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.*;


public class ReceptorDeTemperatura {

    public ReceptorDeTemperatura() {
    }

    public void recibirTemperatura() {
        String topic = "temperaturaSensor";

        CallbackExecution ejecutor = new CallbackExecution() {
            @Override
            public void execute(String message) {
                String temperaturaJson = message;

                Gson gson = new Gson();
                LecturaTemperaturaDTO temperaturaDTO = gson.fromJson(temperaturaJson, LecturaTemperaturaDTO.class);

                ServiceLocator.instanceOf(HeladeraController.class).recibirTemperatura(temperaturaDTO);
            }
        };
        SuscriptorBrocker.suscribirse(topic, ejecutor);
    }

    private MqttCallback configurarCallback(){
        return new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("Conexión de temperatura perdida : " + cause.getMessage());
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {

                String temperaturaJson = new String(message.getPayload());

                Gson gson = new Gson();
                LecturaTemperaturaDTO temperaturaDTO = gson.fromJson(temperaturaJson, LecturaTemperaturaDTO.class);

                ServiceLocator.instanceOf(HeladeraController.class).recibirTemperatura(temperaturaDTO);

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        };
    }

}
