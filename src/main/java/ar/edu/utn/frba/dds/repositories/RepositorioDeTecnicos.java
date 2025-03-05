package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.domain.notificaciones.Notificacion;
import ar.edu.utn.frba.dds.domain.tecnicos.Tecnico;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class RepositorioDeTecnicos implements WithSimplePersistenceUnit {

    public void guardar(Tecnico tecnico) {
        entityManager().persist(tecnico);
    }

    public void modificar(Tecnico tecnico) {
        entityManager().merge(tecnico);
    }

    public void eliminar(Tecnico tecnico) {
        //BAJA LÓGICA
        //servicio.setActivo(false);
        //entityManager().merge(notificacion);
    }

    public void eliminarFisico(Tecnico tecnico) {
        entityManager().remove(tecnico);
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
    public List<Notificacion> buscarPorNombre(String nombre) {
        return entityManager()
                .createQuery("from " + Notificacion.class.getName() + " where nombre = :name")
                .setParameter("name", nombre)
                .getResultList();
    }
}
