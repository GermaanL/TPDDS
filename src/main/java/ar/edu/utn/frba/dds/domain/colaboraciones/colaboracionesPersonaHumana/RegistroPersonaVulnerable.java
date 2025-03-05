package ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaHumana;

import ar.edu.utn.frba.dds.domain.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.domain.tarjetas.TarjetaDePersonaVulnerable;
import ar.edu.utn.frba.dds.domain.personasVulnerables.PersonaVulnerable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "registros_personas_vulnerables")
public class RegistroPersonaVulnerable extends Colaboracion {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personaVulnerable_id", referencedColumnName = "id")
    private PersonaVulnerable personaRegistrada;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarjetaDePersonaVulnerable_id", referencedColumnName = "id")
    private TarjetaDePersonaVulnerable tarjetaDePersonaVulnerable;

    @Override
    public void realizar() {
       //this.getPersonaRegistrada().setTarjeta(this.getTarjetaDePersonaVulnerable());
    }
}



