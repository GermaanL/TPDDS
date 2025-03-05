package ar.edu.utn.frba.dds.domain.suscripciones;

import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import ar.edu.utn.frba.dds.domain.contactos.MedioDeContacto;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.notificaciones.INotificable;
import ar.edu.utn.frba.dds.utils.notificadores.Notificador;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table (name = "suscripciones")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
public abstract class Suscripcion {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "suscriptor_id", referencedColumnName = "id")
    private Colaborador suscriptor;

    @Enumerated(EnumType.STRING)
    @Column(name = "medioDeContacto")
    private MedioDeContacto medioDeContacto;

    @ManyToOne
    @JoinColumn(name = "heladera_id", referencedColumnName = "id")
    private Heladera heladera;

    @Transient
    private Notificador notificador;

    public String getTipo() {
        return this.getClass().getSimpleName();
    }

    public abstract void notificar();
}
