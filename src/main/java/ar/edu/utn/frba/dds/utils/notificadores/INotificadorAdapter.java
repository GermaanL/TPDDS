package ar.edu.utn.frba.dds.utils.notificadores;

import ar.edu.utn.frba.dds.domain.notificaciones.INotificable;

import java.util.List;

public interface INotificadorAdapter {

    public void notificar(INotificable unNotificable, String contenido);
}
