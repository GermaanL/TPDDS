package ar.edu.utn.frba.dds.domain.heladeras.estados;

import java.time.LocalDateTime;

public class HeladeraActiva extends EstadoHeladera{

    public HeladeraActiva(LocalDateTime fecha) {
        super(fecha);
    }

    public LocalDateTime getFecha(){
        return this.fecha;
    }

    @Override
    public String informarEstado() {
        return String.format("La heladera se encuentra activa desde %s.", formatearFecha(this.getFecha()));
    }
}
