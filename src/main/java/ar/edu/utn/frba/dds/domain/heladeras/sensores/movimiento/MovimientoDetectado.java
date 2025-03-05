package ar.edu.utn.frba.dds.domain.heladeras.sensores.movimiento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Entity
//@Table
public class MovimientoDetectado {

    //@Id
    //@GeneratedValue
    private Long id;

    //@Column(name = "fecha_hora", columnDefinition = "DATETIME")
    private LocalDateTime fechaHora;

    //@Column(name = "heladera_id")
    private String idHeladera;

    public MovimientoDetectado(String idHeladera, LocalDateTime fechaHora) {
        this.idHeladera = idHeladera;
        this.fechaHora = fechaHora;
    }
}
