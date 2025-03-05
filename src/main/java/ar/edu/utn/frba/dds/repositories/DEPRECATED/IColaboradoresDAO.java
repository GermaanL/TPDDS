package ar.edu.utn.frba.dds.repositories.DEPRECATED;

import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;

import java.util.List;

public interface IColaboradoresDAO {

    public void save(Colaborador unColaborador);
    public Colaborador find(String colaboradorId);
    public List<Colaborador> findAll();

}
