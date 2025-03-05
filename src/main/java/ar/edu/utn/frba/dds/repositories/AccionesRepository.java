package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.domain.colaboraciones.FormaDeColaboracion;
import ar.edu.utn.frba.dds.domain.sesion.Accion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

public class AccionesRepository extends BaseRepository{
    public Optional<Accion> buscarPorNombre(String uri) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("simple-persistence-unit");
        EntityManager em = emf.createEntityManager();

        return em.createQuery("from acciones where uri = :uri", Accion.class)
                .setParameter("uri", uri)
                .getResultList().stream().findFirst();
    }
}
