package ar.edu.utn.frba.dds.repositories.DEPRECATED;

import ar.edu.utn.frba.dds.domain.heladeras.Heladera;

import java.util.List;

public interface IHeladerasDAO {

    public void save(Heladera unaHeladera);
    public Heladera find(String heladeraId);
    public List<Heladera> findAll();

}
