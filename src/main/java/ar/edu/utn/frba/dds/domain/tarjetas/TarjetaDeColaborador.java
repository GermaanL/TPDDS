package ar.edu.utn.frba.dds.domain.tarjetas;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter

@Entity
@DiscriminatorValue("Colaborador")
public class TarjetaDeColaborador extends Tarjeta{


  @Override
  public Boolean puedeUsarse() {
    return true;
  }

  @Override
  public void usar() {
  }
}
