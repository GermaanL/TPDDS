package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.domain.colaboraciones.FormaDeColaboracion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

public class FormaDeColaboracionRepository extends BaseRepository {
    public Optional<FormaDeColaboracion> buscarPorNombre(String nombre) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("simple-persistence-unit");
        EntityManager em = emf.createEntityManager();

        try {
            em = emf.createEntityManager(); // Crea el EntityManager
            return em.createQuery("from FormaDeColaboracion where nombre = :nombre", FormaDeColaboracion.class)
                    .setParameter("nombre", nombre)
                    .getResultList()
                    .stream()
                    .findFirst();
        } finally {
            if (em != null) {
                em.close(); // Cierra el EntityManager siempre
            }
        }
    }
}
