package ar.edu.utn.frba.dds.domain.sesion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name="acciones") @Table(name="acciones")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Accion {

  @Id
  @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
  private Long id;

  @Column
  private String verbo;

  @Column
  private String uri;

  @ManyToMany(mappedBy = "acciones")
  private List<Rol> roles;

  @ManyToMany(mappedBy = "acciones")
  private List<Usuario> usuarios;

}
