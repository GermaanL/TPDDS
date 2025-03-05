package ar.edu.utn.frba.dds.server.middlewares;

import static java.lang.Thread.sleep;

import ar.edu.utn.frba.dds.domain.sesion.Sesion;
import ar.edu.utn.frba.dds.domain.sesion.Usuario;
import ar.edu.utn.frba.dds.repositories.SesionRepository;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import io.javalin.Javalin;
import java.time.LocalDateTime;
import java.util.List;

public class AutenticadorPermisos {

  private static final List<String> pathsSinPermisos = List.of("/main", "/login", "/registro", "/registroPersonaJuridica", "/", "/sinPermisos", "/logout", "/solicitarTarjeta");
  public static void apply(Javalin app) {
    app.before(ctx -> {
      String sesion_id = ctx.cookieStore().get("sesion_id");
      String pathActual = ctx.path();
      String permiso = ctx.method() + ":" + ctx.path(); //el permiso se forma de verbo http ':' path. Ejemplo: GET:/main

      if (pathActual.contains(".css") || pathActual.contains(".js") || pathActual.contains(".png") || pathActual.contains(".jpg") || pathActual.contains(".ico") || pathActual.contains(".hbs") || pathActual.contains(".html")) {
        return;
      }

      if (sesion_id != null && !sesion_id.isEmpty() && !pathsSinPermisos.contains(pathActual)) {
        Sesion sesion = ServiceLocator.instanceOf(SesionRepository.class).buscarPorId(sesion_id, Sesion.class).get();
        Usuario usuario = sesion.getUsuario();
        //si no tiene permiso
        if (!usuario.getRol().getNombre().equals("admin")  && !usuario.getRol().tienePermiso(permiso) && !usuario.tienePermiso(permiso)) {
            ctx.redirect("/sinPermisos");
        }

        //si tiene permiso pero se termino la sesion
        else if(sesion.getFechaFin().isBefore(LocalDateTime.now())){
          ctx.redirect("/login");
        }
      }
      else if(!pathsSinPermisos.contains(pathActual)) {
        ctx.redirect("/login");
      }
    });
  }

}
