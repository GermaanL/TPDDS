package ar.edu.utn.frba.dds.server.routers;

import ar.edu.utn.frba.dds.server.controllers.ReporteFallaController;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import io.javalin.Javalin;

public class ReporteFallaRouter {
    public static void init (Javalin app){
        app.get("/reporteFallas", ServiceLocator.instanceOf(ReporteFallaController.class)::index);

        app.post("/reporteFallas/guardarFalla", ServiceLocator.instanceOf(ReporteFallaController.class)::save);
    }
}
