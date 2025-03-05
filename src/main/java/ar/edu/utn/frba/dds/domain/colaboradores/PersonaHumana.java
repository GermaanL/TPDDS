package ar.edu.utn.frba.dds.domain.colaboradores;

import ar.edu.utn.frba.dds.domain.documentos.DocumentoDeIdentidad;
import ar.edu.utn.frba.dds.domain.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.domain.colaboraciones.IContribuible;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaHumana.RegistroPersonaVulnerable;
import ar.edu.utn.frba.dds.exceptions.NoTieneDomicilioRegistradoException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "personas_humanas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonaHumana extends Colaborador {

  @Column(name = "nombre")
  private String nombre;

  @Column(name = "apellido")
  private String apellido;

  @Column(name = "fechaNacimiento", columnDefinition = "DATE")
  private LocalDate fechaNacimiento;

  @Embedded
  private DocumentoDeIdentidad documento;

  public void puedeRealizar(IContribuible unaContribucion) {
    if (unaContribucion instanceof RegistroPersonaVulnerable && getDireccion() == null) {
      throw new NoTieneDomicilioRegistradoException("El colaborador: " + getNombre() + " " + getApellido() + " no posee un domicilio registrado para poder llevar a cabo un Registro de persona/s en situación vulnerable");
    }
  }

  @Override
  public boolean esColaboracionDelTipoCorrecto(Colaboracion unaColaboracion) {
    return (!(unaColaboracion.getTipo().getRealizablePor().equals(TipoPersona.HUMANA) ||
            unaColaboracion.getTipo().getRealizablePor().equals(TipoPersona.TODOS)));
  }
}