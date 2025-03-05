package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import ar.edu.utn.frba.dds.repositories.Daos.IDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

public class ColaboradoresRepository extends BaseRepository{

    @SuppressWarnings("unchecked")
    public Optional<Colaborador> buscarPorUsername(String username){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("simple-persistence-unit");
        EntityManager em = emf.createEntityManager();

        return  em.createQuery("from  Colaborador where usuario.username = :username")
                .setParameter("username", username)
                .getResultList().stream().findFirst();
    }

    @SuppressWarnings("unchecked")
    public List<Colaborador> obtenerTodos() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("simple-persistence-unit");
        EntityManager em = emf.createEntityManager();

        // Realiza una consulta para obtener todos los colaboradores
        List<Colaborador> colaboradores = em.createQuery("from Colaborador").getResultList();
        em.close();
        return colaboradores;
    }

}
