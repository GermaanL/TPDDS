package ar.edu.utn.frba.dds.domain.incidentes;

import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("FALLA_TECNICA")
public class FallaTecnica extends Incidente{

    @ManyToOne
    @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
    private Colaborador reportadaPor;

    @Column(name= "descripcion", columnDefinition = "TEXT")
    private String descripcion;

}
