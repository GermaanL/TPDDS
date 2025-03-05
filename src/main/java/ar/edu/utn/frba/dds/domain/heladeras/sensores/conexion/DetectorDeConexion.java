package ar.edu.utn.frba.dds.domain.heladeras.sensores.conexion;

import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

//DEPRECATED
/*
//Se usa para algo ? o se puede borrar? leer enunciado
@Getter
@Setter
//@Entity
//@Table
public class DetectorDeConexion {

    //@Id
    //@GeneratedValue
    private Long id;

    //@OneToOne
    //@JoinColumn(name = "heladera_id")
    private Heladera colocadoEn;

    public Boolean heladeraSinConexion(Heladera unaHeladera, Long tiempoMax){
        LocalDateTime horaUltimaMedicion = this.getColocadoEn().getUltimaMedicionTemperatura().getFechaHora();
        Duration tiempoEntreFechas = Duration.between(horaUltimaMedicion, LocalDateTime.now());
        Long minutos = tiempoEntreFechas.toMinutes();
        return (minutos > tiempoMax);
    }
}
*/