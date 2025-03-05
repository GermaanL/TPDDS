package ar.edu.utn.frba.dds.server.routers;

import ar.edu.utn.frba.dds.server.controllers.HeladeraController;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import io.javalin.Javalin;

public class HeladeraRouter {

  public static void init(Javalin app){

    app.get("/main", ServiceLocator.instanceOf(HeladeraController.class)::index);


    //FALTA TERMINAR
    app.get("/heladeras", ServiceLocator.instanceOf(HeladeraController.class)::misHeladeras);
    app.post("/heladeras/suscripcion", ServiceLocator.instanceOf(HeladeraController.class)::suscribirHeladera);
    app.put("/heladeras/{id}", ServiceLocator.instanceOf(HeladeraController.class)::edit);
    app.delete("/heladeras/{id}", ServiceLocator.instanceOf(HeladeraController.class)::delete);

  }

}
