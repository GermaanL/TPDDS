package ar.edu.utn.frba.dds.domain.colaboradores;

import ar.edu.utn.frba.dds.domain.colaboraciones.Colaboracion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "personas_juridicas")
public class PersonaJuridica extends Colaborador{

  @Column(name = "razonSocial")
  private String razonSocial;

  @Enumerated(EnumType.STRING)
  @Column(name = "tipoJuridico")
  private TipoJuridico tipoJuridico;

  @Column(name = "rubro")
  private String rubro;

  @Override
  public boolean esColaboracionDelTipoCorrecto(Colaboracion unaColaboracion) {
    return (!(unaColaboracion.getTipo().getRealizablePor().equals(TipoPersona.JURIDICA) ||
            unaColaboracion.getTipo().getRealizablePor().equals(TipoPersona.TODOS)));
  }
}

