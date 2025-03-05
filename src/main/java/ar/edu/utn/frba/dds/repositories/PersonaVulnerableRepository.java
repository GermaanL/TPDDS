package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import ar.edu.utn.frba.dds.domain.personasVulnerables.PersonaVulnerable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

public class PersonaVulnerableRepository extends BaseRepository{

  public Optional<PersonaVulnerable> buscarTitularDeTarjeta(long tarjeta_id){
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("simple-persistence-unit");
    EntityManager em = emf.createEntityManager();

    return  em.createQuery("from  PersonaVulnerable where tarjeta.id = :tarjeta_id")
        .setParameter("tarjeta_id", tarjeta_id)
        .getResultList().stream().findFirst();
  }
}
