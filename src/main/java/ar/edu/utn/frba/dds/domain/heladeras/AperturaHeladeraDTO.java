package ar.edu.utn.frba.dds.domain.heladeras;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class AperturaHeladeraDTO {
  public String codigoIdentificador;
  public Long idHeladera;
  public String fechaHoraApertura;
  public int idDonacion;
  public int idSolicitud;
}
