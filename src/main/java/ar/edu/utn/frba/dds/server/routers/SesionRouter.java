package ar.edu.utn.frba.dds.server.routers;

import ar.edu.utn.frba.dds.server.controllers.SesionController;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import io.javalin.Javalin;

public class SesionRouter {
    public static void init(Javalin app){
        app.get("/login", ServiceLocator.instanceOf(SesionController.class)::index);

        app.post("/login", ServiceLocator.instanceOf(SesionController.class)::loginPost);

        app.get("/registro", ServiceLocator.instanceOf(SesionController.class)::create);
        app.post("/registro", ServiceLocator.instanceOf(SesionController.class)::saveRegistro);

        app.get("/logout", ServiceLocator.instanceOf(SesionController.class)::logout);

        app.get("/registroPersonaJuridica" , ServiceLocator.instanceOf(SesionController.class)::createPersonaJuridica);
        app.post("/registroPersonaJuridica", ServiceLocator.instanceOf(SesionController.class)::savePersonaJuridica);

        app.get("/sinPermisos", ServiceLocator.instanceOf(SesionController.class)::sinPermiso);

        //app.put("/heladeras/{id}", ServiceLocator.instanceOf(SesionController.class)::edit);

        //app.delete("/heladeras/{id}", ServiceLocator.instanceOf(SesionController.class)::delete);

    }
}
