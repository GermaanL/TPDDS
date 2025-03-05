package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import ar.edu.utn.frba.dds.domain.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.repositories.DEPRECATED.ITecnicosDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

public class TecnicosRepository extends BaseRepository{

    @SuppressWarnings("unchecked")
    public Optional<Tecnico> buscarPorUsername(String username){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("simple-persistence-unit");
        EntityManager em = emf.createEntityManager();

        return  em.createQuery("from Tecnico where usuario.username = :username")
                .setParameter("username", username)
                .getResultList().stream().findFirst();
    }
}
