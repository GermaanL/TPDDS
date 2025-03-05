package ar.edu.utn.frba.dds.domain.suscripciones;

import ar.edu.utn.frba.dds.utils.notificadores.Notificador;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static net.bytebuddy.agent.builder.AgentBuilder.Default.of;

@Getter
@Setter
@Entity
@DiscriminatorValue("poco_stock")
public class PocoStock extends Suscripcion{
    @Column(name = "viandas_poco_stock")
    private Integer viandas_poco_stock;

    @Override
    public void notificar() {
        this.setNotificador(Notificador.of(this.getMedioDeContacto()));
        this.getNotificador().notificar(this.getSuscriptor(), "Hay poco stock de viandas en la heladera " + this.getHeladera().getNombre());
    }
}
