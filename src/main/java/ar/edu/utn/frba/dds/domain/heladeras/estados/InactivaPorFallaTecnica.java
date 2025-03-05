package ar.edu.utn.frba.dds.domain.heladeras.estados;

import java.time.LocalDateTime;

public class InactivaPorFallaTecnica extends EstadoHeladera {

    public InactivaPorFallaTecnica() {
        super();
    }

    public LocalDateTime getFecha(){
        return this.fecha;
    }

    @Override
    public String informarEstado() {
        return String.format("La heladera se encuentra inactiva desde %s por fallas técnicas detectadas por un colaborador.", formatearFecha(this.getFecha()));
    }
}
