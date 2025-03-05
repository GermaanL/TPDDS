package ar.edu.utn.frba.dds.domain.suscripciones;

import ar.edu.utn.frba.dds.utils.notificadores.Notificador;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
@DiscriminatorValue("mucho_stock")
public class MuchoStock extends Suscripcion{
    @Column(name = "viandas_mucho_stock")
    private Integer viandas_mucho_stock;

    @Override
    public void notificar() {
        this.setNotificador(Notificador.of(this.getMedioDeContacto()));
        this.getNotificador().notificar(this.getSuscriptor(), "Hay mucho stock de viandas en la heladera " + this.getHeladera().getNombre());
    }
}
