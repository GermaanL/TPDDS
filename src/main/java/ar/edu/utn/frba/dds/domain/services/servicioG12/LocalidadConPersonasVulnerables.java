package ar.edu.utn.frba.dds.domain.services.servicioG12;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class LocalidadConPersonasVulnerables {
  private String localidad;
  private String personas;
  private Integer cantidad_personas;

}
