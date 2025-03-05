package ar.edu.utn.frba.dds.domain.incidentes;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("ALERTA")
public class Alerta extends Incidente{

    @Enumerated(EnumType.STRING)
    private TipoAlerta tipoAlerta;

}
