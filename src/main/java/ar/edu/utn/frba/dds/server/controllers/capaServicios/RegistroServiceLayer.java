package ar.edu.utn.frba.dds.server.controllers.capaServicios;

import ar.edu.utn.frba.dds.domain.sesion.Usuario;
import ar.edu.utn.frba.dds.repositories.SesionRepository;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import ar.edu.utn.frba.dds.utils.ValidadorContrasenias;
import io.javalin.http.Context;
import java.util.Map;

public class RegistroServiceLayer {

  public static boolean validarContrasenia(Context context) {
    return ValidadorContrasenias.permitirContrasenia(context.formParam("password"));
  }

  public static boolean validarUsuario(Context context) {
    return ServiceLocator.instanceOf(SesionRepository.class).buscarPorId(context.formParam("username"), Usuario.class).isPresent();
  }
}