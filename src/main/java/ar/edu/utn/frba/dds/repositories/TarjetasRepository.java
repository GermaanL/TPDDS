package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import ar.edu.utn.frba.dds.domain.tarjetas.EstadoTarjeta;
import ar.edu.utn.frba.dds.domain.tarjetas.SeguimientoTarjeta;
import ar.edu.utn.frba.dds.domain.tarjetas.Tarjeta;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

public class TarjetasRepository extends BaseRepository{

  public Optional<Tarjeta> buscarPorCodigo(String codigoIdentificador){
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("simple-persistence-unit");
    EntityManager em = emf.createEntityManager();

    return  em.createQuery("from  Tarjeta where codigoIdentificador = :codigoIdentificador")
        .setParameter("codigoIdentificador", codigoIdentificador)
        .getResultList().stream().findFirst();
  }

  public Optional<List<Colaborador>> tarjetasAsignadas(){
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("simple-persistence-unit");
    EntityManager em = emf.createEntityManager();

    EstadoTarjeta estadoAsignada = EstadoTarjeta.Asignada;

    return Optional.of(em.createQuery("from  Colaborador where tarjeta.estado_actual = :estadoAsignada")
        .setParameter("estadoAsignada", estadoAsignada)
        .getResultList().stream().toList());
  }


}
