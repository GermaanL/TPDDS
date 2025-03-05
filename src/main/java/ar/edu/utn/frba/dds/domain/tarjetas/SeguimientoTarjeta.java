package ar.edu.utn.frba.dds.domain.tarjetas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity(name = "seguimiento_tarjeta")
public class SeguimientoTarjeta {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "tarjeta_id")
  private Tarjeta Tarjeta;

  @Enumerated(EnumType.STRING)
  private EstadoTarjeta estado;

  @Column
  private LocalDateTime fechaHora;

}
