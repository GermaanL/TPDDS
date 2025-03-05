package ar.edu.utn.frba.dds.domain.sesion;

import ar.edu.utn.frba.dds.repositories.SesionRepository;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sesiones")
public class Sesion {

  @Id
  String sesion_id;

  @Getter
  @OneToOne
  @JoinColumn(name = "usuario_id", referencedColumnName = "username")
  Usuario usuario;

  @Column
  LocalDateTime fechaInicio;

  @Getter
  @Column
  LocalDateTime fechaFin;

  public static void iniciarSesion (Context context){
    SesionRepository sesionRepository =  ServiceLocator.instanceOf(SesionRepository.class);
    Usuario usuario = sesionRepository.buscarPorId(context.formParam("username"), Usuario.class).get();

    sesionRepository.buscarSesionPorUsername(usuario.getUsername()).ifPresent(sesionRepository::eliminar);
    Sesion sesion = new Sesion(generarUUID(), usuario, LocalDateTime.now(), LocalDateTime.now().plusDays(1));

    context.cookieStore().clear();

    context.cookieStore().set("sesion_id", sesion.sesion_id);
    sesionRepository.guardar(sesion);
  }

  public static String generarUUID(){
    UUID uuid = UUID.randomUUID();
    SesionRepository sesionRepository =  ServiceLocator.instanceOf(SesionRepository.class);

    while(sesionRepository.buscarPorId(uuid.toString(), Sesion.class).isPresent()){
      uuid = UUID.randomUUID();
    }

    return uuid.toString();
  }

  /**
   * Le pasas el sesion_id de la cookie y te devuelve el username que tiene asociado (string).
   * @return retorna el string del username o null si no existe la sesion.
   * */
  public static String obtenerUsernameDesdeSesionID(String sesion_id){
    if (sesion_id == null) return null;
    return ServiceLocator.instanceOf(SesionRepository.class).buscarPorId(sesion_id, Sesion.class).isPresent() ?
        ServiceLocator.instanceOf(SesionRepository.class).buscarPorId(sesion_id, Sesion.class).get().getUsuario().getUsername() : null;
  }

  public static void main (String[] args){
    System.out.println(generarUUID());
    SesionRepository sesionRepository =  ServiceLocator.instanceOf(SesionRepository.class);
    Usuario usuario = sesionRepository.buscarPorId("jmenes", Usuario.class).get();

    Sesion sesion = sesionRepository.buscarSesionPorUsername(usuario.getUsername()).get();
    System.out.println("uuid: "+ sesion.sesion_id);

    sesionRepository.eliminar(sesion);
    System.out.println("Sesion eliminada");


//    SesionRepository sesionRepository =  ServiceLocator.instanceOf(SesionRepository.class);
//    Usuario usuario = sesionRepository.buscarPorId("jmenes", Usuario.class).get();
//    Sesion sesion = new Sesion(generarUUID(), usuario, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
//    sesionRepository.guardar(sesion);

  }

}
