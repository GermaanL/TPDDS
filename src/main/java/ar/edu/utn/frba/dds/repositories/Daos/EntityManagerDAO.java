package ar.edu.utn.frba.dds.repositories.Daos;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.Getter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EntityManagerDAO implements WithSimplePersistenceUnit, IDAO{

  @Getter
  private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("simple-persistence-unit");

  @Override
  public <T> void create(T objeto) {

    withTransaction(() -> {
      entityManager().persist(objeto);
    });
  }
//  @Override
//  public <T> void create(T objeto) {
//    EntityManager em = emf.createEntityManager();
//    EntityTransaction transaction = null;
//    try {
//      transaction = em.getTransaction();
//      transaction.begin();
//      em.persist(objeto);
//      transaction.commit();
//    } catch (Exception e) {
//      if (transaction != null && transaction.isActive()) {
//        transaction.rollback();
//      }
//      e.printStackTrace();
//    } finally {
//      em.close();
//    }
//  }

  @Override
  public <T> Optional<T> find(long id, Class<T> clase) {
    return Optional.ofNullable(entityManager().find(clase, id));
  }

  public <T> Optional<T> find(String id, Class<T> clase) {
    return Optional.ofNullable(entityManager().find(clase, id));
  }

//  public <T> Optional<T> find(String id, Class<T> clase) {
//    EntityManager em = emf.createEntityManager();
//    return Optional.ofNullable(em.find(clase, id));
//  }

  @Override
  public <T> void update(T objeto) {
    withTransaction(() -> {
      entityManager().merge(entityManager().contains(objeto) ? objeto : entityManager().merge(objeto));
    });
  }

  @Override
  public <T> void delete(T objeto) {
    withTransaction(() -> {
      entityManager().remove(entityManager().contains(objeto) ? objeto : entityManager().merge(objeto));
    });
  }


//  @Override
//  public <T> void delete(T objeto) {
//    EntityManager em = emf.createEntityManager();
//    EntityTransaction transaction = null;
//    try {
//      transaction = em.getTransaction();
//      transaction.begin();
//      em.remove(em.contains(objeto) ? objeto : em.merge(objeto));
//      transaction.commit();
//    } catch (Exception e) {
//      if (transaction != null && transaction.isActive()) {
//        transaction.rollback();
//      }
//      e.printStackTrace();
//    } finally {
//      em.close();
//    }
//
//  }

  @Override
  public <T> List<T> findAll(Class<T> clase) {
    return entityManager()
            .createQuery("from " + clase.getName())
            .getResultList();
  }

  public void close() {
    if (entityManager() != null && entityManager().isOpen()) {
      entityManager().close();
    }
  }
}