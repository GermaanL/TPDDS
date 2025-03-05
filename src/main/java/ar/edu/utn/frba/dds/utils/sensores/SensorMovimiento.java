package ar.edu.utn.frba.dds.utils.sensores;

import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import lombok.Getter;
import lombok.Setter;

public class SensorMovimiento {

    @Getter private Heladera colocadoEn;
    @Getter @Setter private String movimientoDetectado;
    @Getter private PublicadorBrokerDEPRECATED publicadorBroker;

    public void recibirMovimientoDetectado(){
        // Recibimos un movimiento fraudulento detectado en formato JSON.
        // Ejemplo:
        // {"idHeladera": "H123", "fechaHora": "2024-06-30 01:08:45"}
        String jsonMovimiento = GeneradorDeMovimiento.generarJson(this.getColocadoEn().getId().toString());
        this.setMovimientoDetectado(jsonMovimiento);
    }

    public void enviarMovimientoDetectado(){
        String topic = "movimientos/H" + this.getColocadoEn().getId().toString();
        String clientId = "sensorMov/H" + this.getColocadoEn().getId().toString();
        String message = this.getMovimientoDetectado();
        this.publicadorBroker.publicar(topic, clientId, message);
    }

}
