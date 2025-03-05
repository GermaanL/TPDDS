package ar.edu.utn.frba.dds.utils.serviceConnector;

import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.personasVulnerables.PersonaVulnerable;
import ar.edu.utn.frba.dds.domain.tarjetas.UsoDeTarjeta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ServiceG12DTO {
    private List<Heladera> heladeras;
    private List<PersonaVulnerable> personasVulnerables;
    private List<UsoDeTarjeta> usosDeTarjeta;
}
