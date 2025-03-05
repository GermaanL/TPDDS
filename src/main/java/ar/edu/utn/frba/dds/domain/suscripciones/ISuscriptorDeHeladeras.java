package ar.edu.utn.frba.dds.domain.suscripciones;

import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.notificaciones.INotificable;

//DEPRECATED
public interface ISuscriptorDeHeladeras extends INotificable {
    Boolean correspondeNotificarPocoStockDe(Heladera unaHeladera);

    Boolean correspondeNotificarMuchoStockDe(Heladera unaHeladera);

    Boolean correspondeNotificarInactividadDe(Heladera unaHeladera);
}
