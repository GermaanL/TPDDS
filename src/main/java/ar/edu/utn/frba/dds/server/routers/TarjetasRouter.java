package ar.edu.utn.frba.dds.server.routers;

import ar.edu.utn.frba.dds.server.controllers.AdminController;
import ar.edu.utn.frba.dds.server.controllers.TarjetaController;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import io.javalin.Javalin;

public class TarjetasRouter {
  public static void init(Javalin app) {

    app.get("solicitarTarjeta", ServiceLocator.instanceOf(TarjetaController.class)::solicitarTarjeta);
    app.get("autorizarTarjetaView", ServiceLocator.instanceOf(TarjetaController.class)::autorizarTarjetaView);
    app.post("autorizarTarjeta", ServiceLocator.instanceOf(TarjetaController.class)::autorizarTarjeta);
    app.post("despacharTarjeta/{id}", ServiceLocator.instanceOf(TarjetaController.class)::despacharTarjeta);


  }
}
