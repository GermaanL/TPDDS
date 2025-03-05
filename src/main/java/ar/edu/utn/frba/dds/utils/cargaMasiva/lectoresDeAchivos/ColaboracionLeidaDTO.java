package ar.edu.utn.frba.dds.utils.cargaMasiva.lectoresDeAchivos;

import ar.edu.utn.frba.dds.domain.documentos.TipoDoc;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;

@Builder @Getter
public class ColaboracionLeidaDTO {

  private TipoDoc tipoDoc;
  private String documento;
  private String nombre;
  private String apellido;
  private String mail;
  private LocalDate fechaColaboracion;
  private String formaColaboracion;
  private Integer cantidad;



}
