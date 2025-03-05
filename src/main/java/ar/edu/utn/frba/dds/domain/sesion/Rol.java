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
@Table(name = "rol")
@Getter @Setter
public class Rol {

  @Id
  private Long id;

  @Column
  private String nombre;

  @ManyToMany
  @JoinTable(
      name = "roles_acciones",
      joinColumns = @JoinColumn(name = "rol_id"),
      inverseJoinColumns = @JoinColumn(name = "accion_id")
  )
  private List<Accion> acciones;

//  public boolean tienePermiso(String permiso) {
//    return acciones.stream().anyMatch(p -> (p.getVerbo() + ":" + p.getUri()).equalsIgnoreCase(permiso));
//  }

  public boolean tienePermiso(String permiso) {

    //System.out.print("PERMISO: " + permiso + "\n");
    return acciones.stream().anyMatch(p -> {
      String uriPattern = p.getUri()
              .replace("{id}", "\\d+") // Reemplaza {id} por un patrón numérico.
              .replace("*", ".*"); // Agrega soporte para comodines '*' si es necesario.
      String regex = p.getVerbo().toUpperCase() + ":" + uriPattern; // Permite cualquier sufijo adicional.

      //System.out.print("REGEX: " + regex + "\n");
      //System.out.print("MATCH: " + permiso.matches(regex) + "\n");

      return permiso.matches(regex);
    });
  }
}
