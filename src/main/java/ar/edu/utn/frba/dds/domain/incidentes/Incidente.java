package ar.edu.utn.frba.dds.domain.incidentes;

import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.tecnicos.Tecnico;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "incidentes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class Incidente {

    @Id
    @GeneratedValue
    protected Long id;

    @Column(name = "fechaAlta", columnDefinition = "TIMESTAMP")
    protected LocalDateTime fechaAlta;

    @OneToOne
    @JoinColumn(name = "heladeraAfectada_id", referencedColumnName = "id")
    protected Heladera heladeraAfectada;

    @Column(name = "solucionado")
    protected Boolean solucionado;

    @Column(name = "fechaSolucion", columnDefinition = "TIMESTAMP")
    protected LocalDateTime fechaSolucion;

    @Enumerated(EnumType.STRING)
    protected TipoIncidente tipoIncidente;

    @Column(name = "imagen")
    private String imagen;


}
