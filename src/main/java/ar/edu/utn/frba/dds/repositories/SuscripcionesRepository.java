package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import ar.edu.utn.frba.dds.domain.suscripciones.Suscripcion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class SuscripcionesRepository extends BaseRepository{

    public List<Suscripcion> buscarSuscripcionesPorColaborador(Colaborador colaborador) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("simple-persistence-unit");
        EntityManager em = emf.createEntityManager();

        List<Suscripcion> suscripciones = em.createQuery(
                        "SELECT s FROM Suscripcion s WHERE s.suscriptor = :colaborador", Suscripcion.class)
                .setParameter("colaborador", colaborador)
                .getResultList();

        em.close();
        emf.close();
        return suscripciones;
    }
}
