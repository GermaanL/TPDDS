package ar.edu.utn.frba.dds.domain.tarjetas;

import ar.edu.utn.frba.dds.domain.colaboradores.PersonaHumana;
import ar.edu.utn.frba.dds.domain.personasVulnerables.PersonaVulnerable;
import ar.edu.utn.frba.dds.repositories.PersonaVulnerableRepository;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import ar.edu.utn.frba.dds.utils.GeneradorDeCodigo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("PersonaVulnerable")
public class TarjetaDePersonaVulnerable extends Tarjeta{

    @Column(name = "usosIniciales")
    private Integer usosIniciales;

    @Column(name = "usosRestantes")
    private Integer usosRestantes;


    @ManyToOne
    @JoinColumn(name = "entregadaPorPersonaHumana_id", referencedColumnName = "id")
    private PersonaHumana entregadaPor;

    private Integer cantidadDeUsos() {
        PersonaVulnerable titular = ServiceLocator.instanceOf(PersonaVulnerableRepository.class).buscarTitularDeTarjeta(this.id).get();
        return this.getUsosIniciales() + (titular.getCantidadMenoresACargo() * 2);
    }


    @Override
    public Boolean puedeUsarse() {
        return this.getUsosRestantes() > 0;
    }

    @Override
    public void usar() {
        if(this.puedeUsarse()) this.setUsosRestantes(this.getUsosRestantes() - 1);
    }
}
