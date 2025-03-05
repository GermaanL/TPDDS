package ar.edu.utn.frba.dds.domain.lugares;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class  AreaDeCobertura {

  @Embedded
  private Coordenada coordenada;

  @Column(name = "radioCoberturaEnKm")
  private Double radioCoberturaEnKm;

}
