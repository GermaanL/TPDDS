package ar.edu.utn.frba.dds.domain.suscripciones;

import ar.edu.utn.frba.dds.utils.notificadores.Notificador;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("desperfecto")
public class Desperfecto extends Suscripcion{
    @Override
    public void notificar() {
        this.setNotificador(Notificador.of(this.getMedioDeContacto()));
        this.getNotificador().notificar(this.getSuscriptor(), "Hay mucho stock de viandas en la heladera " + this.getHeladera().getNombre());
    }
}
