package ar.edu.utn.frba.dds.cronjobs;
/*
import ar.edu.utn.frba.dds.controllers.GestorDeIncidentes;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.heladeras.sensores.conexion.DetectorDeConexion;
import ar.edu.utn.frba.dds.repositories.HeladerasRepository;
import lombok.Getter;

import java.util.List;

public class GestorDeConexion {

    @Getter
    private HeladerasRepository repositorioDeHeladeras;
    @Getter
    private DetectorDeConexion detectorDeConexion;
    @Getter
    private GestorDeIncidentes gestorDeIncidentes;

    public void controlarConexion(Long tiempoMaxSinNovedades) {
        List<Heladera> heladeras = this.repositorioDeHeladeras.buscarTodos(Heladera.class);
        heladeras.parallelStream().forEach(h -> {
            Boolean noTieneConexion = this.getDetectorDeConexion().heladeraSinConexion(h, tiempoMaxSinNovedades);
            if (noTieneConexion) {
                //this.getGestorDeIncidentes().reportarAlerta(h, "ALERTA_CONEXION");
            }
        });
    }
}
*/