package ar.edu.utn.frba.dds.repositories.DEPRECATED;

import ar.edu.utn.frba.dds.domain.tecnicos.Tecnico;

import java.util.List;

public interface ITecnicosDAO {

    public void saveTecnico(Tecnico unTecnico);
    public List<Tecnico> findAllTecnicos();

}
