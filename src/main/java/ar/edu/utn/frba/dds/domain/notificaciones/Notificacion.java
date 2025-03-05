package ar.edu.utn.frba.dds.domain.notificaciones;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notificaciones")
@Builder
@ToString
public class Notificacion {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "fechaHora", columnDefinition = "TIMESTAMP")
  private LocalDateTime fechaHora;

  @Column(name = "tipo")
  private String tipo;

  @Transient
  private INotificable destinatario;

  @Column(name = "contenido", columnDefinition = "TEXT")
  private String contenido;
}
