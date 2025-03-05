package ar.edu.utn.frba.dds.server.routers;

import ar.edu.utn.frba.dds.server.controllers.ReparacionesController;
import ar.edu.utn.frba.dds.server.controllers.TarjetaController;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import io.javalin.Javalin;

public class TecnicoRouter {
    public static void init(Javalin app) {

        app.get("/misReparaciones",ServiceLocator.instanceOf(ReparacionesController.class)::index);
        app.get("/reparaciones/falla/{id}", ServiceLocator.instanceOf(ReparacionesController.class)::mostrarFalla);
        app.post("/misReparaciones/realizarVisita", ServiceLocator.instanceOf(ReparacionesController.class)::realizarVisita);
        app.get("/reparacionesRealizadas", ServiceLocator.instanceOf(ReparacionesController.class)::verReparaciones);
    }
}
