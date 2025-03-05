package ar.edu.utn.frba.dds.domain.heladeras.estados;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class EstadoHeladera {

    protected LocalDateTime fecha;

    public EstadoHeladera(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    public EstadoHeladera() {
        this.fecha = LocalDateTime.now();
    }

    protected String formatearFecha(LocalDateTime unaFecha){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return unaFecha.format(formato);
    }

    public abstract String informarEstado();
}
