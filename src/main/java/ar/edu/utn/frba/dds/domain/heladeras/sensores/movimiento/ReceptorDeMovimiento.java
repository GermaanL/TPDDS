package ar.edu.utn.frba.dds.domain.heladeras.sensores.movimiento;

import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.utils.LocalDateTimeAdapterJson;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.time.LocalDateTime;
import java.util.Stack;

public class ReceptorDeMovimiento {

    @Getter private Heladera heladeraAsociada;
    @Getter private Stack<MovimientoDetectado> movimientosDetectados;
    @Getter private SuscriptorBrokerDEPRECATED suscriptorBroker;

    public void recibirMovimiento() {

        String topic = "movimientos/H" + this.getHeladeraAsociada().getId().toString();
        String clientId = "receptorMov/H" + this.getHeladeraAsociada().getId().toString();
        MqttCallback callback = this.configurarCallback();
        //this.suscriptorBroker.suscribirse(topic, clientId, callback);
    }

    private MqttCallback configurarCallback(){
        return new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("Conexión perdida: " + cause.getMessage());
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String movimientoJson = new String(message.getPayload());
                // Deserializar el JSON recibido
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapterJson())
                        .create();
                MovimientoDetectado movimientoDetectado = gson.fromJson(movimientoJson, MovimientoDetectado.class);
                // Se guarda el movimiento en una pila, para tener en el tope el más reciente
                movimientosDetectados.push(movimientoDetectado);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        };
    }


}
