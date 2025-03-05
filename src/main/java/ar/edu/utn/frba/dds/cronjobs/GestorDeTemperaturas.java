package ar.edu.utn.frba.dds.cronjobs;
/*
import ar.edu.utn.frba.dds.controllers.GestorDeIncidentes;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.heladeras.sensores.temperatura.DetectorDeTemperatura;
import ar.edu.utn.frba.dds.domain.heladeras.sensores.temperatura.MedicionTemperatura;
import ar.edu.utn.frba.dds.repositories.HeladerasRepository;
import lombok.Getter;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class GestorDeTemperaturas {

    @Getter private HeladerasRepository repositorioDeHeladeras;
    @Getter private DetectorDeTemperatura detectorDeTemperatura;
    @Getter private GestorDeIncidentes gestorDeIncidentes;

    public void actualizarTemperaturas(){
        List<Heladera> heladeras = this.repositorioDeHeladeras.buscarTodos(Heladera.class);
        heladeras.parallelStream().forEach(h -> {
            Stack<MedicionTemperatura> temperaturas = h.getReceptorDeTemperatura().getTemperaturasRecibidas();
            try {
                MedicionTemperatura ultimaMedicion = temperaturas.pop();
                temperaturas.clear();
                this.analizarTemperaturaDe(h, ultimaMedicion);
                this.actualizarTemperaturas();
            } catch (EmptyStackException e) {
                e.getMessage();
                System.out.println("No hay temperaturas nuevas");
            }
        });
    }

    public void analizarTemperaturaDe(Heladera unaHeladera, MedicionTemperatura unaTemperatura){
        Boolean tempFueraDeRango = this.getDetectorDeTemperatura().temperaturaFueraDeRango(unaTemperatura, unaHeladera);
        if(tempFueraDeRango){
            //this.getGestorDeIncidentes().reportarAlerta(unaHeladera, "ALERTA_TEMP");
        }
    }
}
*/