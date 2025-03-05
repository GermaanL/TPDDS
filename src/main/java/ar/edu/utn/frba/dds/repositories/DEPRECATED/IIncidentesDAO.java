package ar.edu.utn.frba.dds.repositories.DEPRECATED;

import ar.edu.utn.frba.dds.domain.incidentes.Alerta;
import ar.edu.utn.frba.dds.domain.incidentes.FallaTecnica;

import java.util.List;

public interface IIncidentesDAO {

    public void saveAlerta(Alerta unaAlerta);
    public void saveFalla(FallaTecnica unaFalla);
    public List<Alerta> findAllAlertas();
    public List<FallaTecnica> findAllFallas();

}
