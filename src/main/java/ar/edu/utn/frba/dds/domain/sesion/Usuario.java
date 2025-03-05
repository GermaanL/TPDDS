package ar.edu.utn.frba.dds.domain.sesion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "usuarios")
public class Usuario {
  @Id
  @Column(name = "username")
  private String username; //este va a ser el identificador y será unico por colaborador, asi como el nombre de usuario en ig

  @Column(name = "password")
  private String password;

  //REVISAR cascade!!! TODO
  @OneToOne
  @JoinColumn(name = "rol_id", referencedColumnName = "id")
  private Rol rol;

  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(
      name = "usuarios_acciones",
      joinColumns = @JoinColumn(name = "usuario_id"),
      inverseJoinColumns = @JoinColumn(name = "accion_id")
  )
  private List<Accion> acciones;

  public boolean tienePermiso(String permiso) {
    return acciones.stream().anyMatch(p -> (p.getVerbo() + ":" + p.getUri()).equalsIgnoreCase(permiso));
  }

}
