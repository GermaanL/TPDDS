package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.domain.notificaciones.Notificacion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class RepositorioDeNotificaciones implements WithSimplePersistenceUnit {

    public void guardar(Notificacion notificacion) {
        entityManager().persist(notificacion);
    }

    public void modificar(Notificacion notificacion) {
        entityManager().merge(notificacion);
    }

    public void eliminar(Notificacion notificacion) {
        //BAJA LÓGICA
        //servicio.setActivo(false);
        //entityManager().merge(notificacion);
    }

    public void eliminarFisico(Notificacion notificacion) {
        entityManager().remove(notificacion);
    }

    public Optional<Notificacion> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(Notificacion.class, id));
    }

    @SuppressWarnings("unchecked")
    public List<Notificacion> buscarTodos() {
        return entityManager()
                .createQuery("from " + Notificacion.class.getName())
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Notificacion> buscarPorTipo(String tipo) {
        return entityManager()
                .createQuery("from " + Notificacion.class.getName() + " where tipo = :tipo")
                .setParameter("tipo", tipo)
                .getResultList();
    }
}
