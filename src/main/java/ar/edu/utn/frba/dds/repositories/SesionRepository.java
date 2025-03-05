package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import ar.edu.utn.frba.dds.domain.sesion.Sesion;
import ar.edu.utn.frba.dds.domain.sesion.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

public class SesionRepository extends BaseRepository{

  private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("simple-persistence-unit");

  public Optional<Sesion> buscarSesionPorUsername(String username){
    EntityManager em = emf.createEntityManager();

    return  em.createQuery("from  sesiones where usuario.username = :username")
        .setParameter("username", username)
        .getResultList().stream().findFirst();
  }

}
