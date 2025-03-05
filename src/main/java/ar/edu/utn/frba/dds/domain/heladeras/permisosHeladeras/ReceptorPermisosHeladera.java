package ar.edu.utn.frba.dds.domain.heladeras.permisosHeladeras;

import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.heladeras.SolicitudApertura;

import ar.edu.utn.frba.dds.utils.LocalDateTimeAdapterJson;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import java.time.LocalDateTime;

public class ReceptorPermisosHeladera extends Thread{

  @Getter
  private Heladera heladeraAsociada;
  @Getter
  private SuscriptorBrokerDEPRECATED suscriptorBroker;

  public ReceptorPermisosHeladera(Heladera heladera) {
    this.heladeraAsociada = heladera;
  }

  public void recibirPermisos() {
    String topic = "permisos/H" + this.getHeladeraAsociada().getId().toString();
    String clientId = "receptorPermisos/H" + this.getHeladeraAsociada().getId().toString();
    MqttCallback callback = this.configurarCallback();
   // this.suscriptorBroker.suscribirse(topic, clientId, callback);
  }

  //region Funciones Auxiliares
  private MqttCallback configurarCallback(){
    return new MqttCallback() {
      @Override
      public void connectionLost(Throwable cause) {
        System.out.println("Conexión perdida: " + cause.getMessage());
      }

      @Override
      public void messageArrived(String topic, MqttMessage message) throws Exception {
        String permisoJson = new String(message.getPayload());
        // Deserializar
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapterJson()).create();
        SolicitudApertura solicitud = gson.fromJson(permisoJson, SolicitudApertura.class);
        // Guardar el permiso en una lista de la heladera
        heladeraAsociada.agregarPermisoApertura(solicitud);
      }

      @Override
      public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {}
    };
  }

  @Override
  public void run() {
    recibirPermisos();
  }

  //endregion
}
