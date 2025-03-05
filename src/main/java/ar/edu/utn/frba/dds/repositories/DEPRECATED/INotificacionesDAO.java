package ar.edu.utn.frba.dds.repositories.DEPRECATED;

import ar.edu.utn.frba.dds.domain.notificaciones.Notificacion;

import java.util.List;

public interface INotificacionesDAO {

    public void save(Notificacion unaNotificacion);
    public List<Notificacion> findAll();
}
