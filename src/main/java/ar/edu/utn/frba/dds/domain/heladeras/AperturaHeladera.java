package ar.edu.utn.frba.dds.domain.heladeras;

import ar.edu.utn.frba.dds.domain.tarjetas.Tarjeta;
import ar.edu.utn.frba.dds.domain.tarjetas.TarjetaDeColaborador;
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
@Table(name = "aperturas_heladera")
public class AperturaHeladera {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  @JoinColumn(name = "heladera_id")
  private Heladera heladera;

  @Column(name = "fechaHoraApertura", columnDefinition = "TIMESTAMP")
  private LocalDateTime fechaHoraApertura;

  @ManyToOne
  @JoinColumn(name = "tarjeta_id")
  private Tarjeta tarjeta;

}
