package ar.edu.utn.frba.dds.domain.tecnicos;

import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.incidentes.Incidente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "visitas_tecnicas")
public class VisitaTecnica {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "fechaHora", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaVisita;

    @ManyToOne
    @JoinColumn(name = "tecnico_id", referencedColumnName = "id")
    private Tecnico tecnico;

    @ManyToOne
    @JoinColumn(name = "heladera_id", referencedColumnName = "id")
    private Heladera heladera;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "incidente_id", referencedColumnName = "id")
    private Incidente causa;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "incidenteSolucionado")
    private Boolean incidenteSolucionado;

    @Column(name = "foto", columnDefinition = "TEXT")
    private String foto;

}
