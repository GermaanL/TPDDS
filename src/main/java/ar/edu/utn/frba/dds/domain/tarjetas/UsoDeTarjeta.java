package ar.edu.utn.frba.dds.domain.tarjetas;

import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usos_de_tarjeta")
public class UsoDeTarjeta {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tarjetaUsada_id", referencedColumnName = "id")
    private Tarjeta tarjetaUsada;

    @Column(name = "fechaHora", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "heladeraUsada_id", referencedColumnName = "id")
    private Heladera heladeraUsada;

}
