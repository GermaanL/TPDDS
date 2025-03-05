package ar.edu.utn.frba.dds.domain.heladeras.sensores.temperatura;

import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
@Setter
@Getter
@Entity
@Table (name = "lecturas_temperatura")
public class LecturaDeTemperatura {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "valor_en_grados")
    private Double valorEnGrados;

    @Column(name = "fecha_hora", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "heladera_id", referencedColumnName = "id")
    private Heladera heladeraAsociada;
}
