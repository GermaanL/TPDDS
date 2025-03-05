package ar.edu.utn.frba.dds.repositories.DEPRECATED;


import ar.edu.utn.frba.dds.domain.tecnicos.VisitaTecnica;

import java.util.List;

public interface IVisitasTecnicasDAO {

    public void save(VisitaTecnica unaHeladera);
    public VisitaTecnica find(String visitaId);
    public List<VisitaTecnica> findAll();
}
