package ar.edu.utn.frba.dds.server.routers;

import ar.edu.utn.frba.dds.server.controllers.PersonaVulnerableController;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import io.javalin.Javalin;

public class PersonaVulnerableRouter {
    public static void init(Javalin app){
        app.get("/registroPersonaVulnerable", ServiceLocator.instanceOf(PersonaVulnerableController.class)::create);
        //app.post("/registroPersonaVulnerable", ServiceLocator.instanceOf(PersonaVulnerableController.class)::save);
    }
}
