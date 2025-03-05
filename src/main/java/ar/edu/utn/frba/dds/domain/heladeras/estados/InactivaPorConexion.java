package ar.edu.utn.frba.dds.domain.heladeras.estados;

import java.time.LocalDateTime;

public class InactivaPorConexion extends EstadoHeladera {
    public InactivaPorConexion() {
        super();
    }

    public LocalDateTime getFecha(){
        return this.fecha;
    }

    @Override
    public String informarEstado() {
        return String.format("La heladera se encuentra inactiva desde %s por fallas detectadas por el sensor de conexión.", formatearFecha(this.getFecha()));
    }
}
