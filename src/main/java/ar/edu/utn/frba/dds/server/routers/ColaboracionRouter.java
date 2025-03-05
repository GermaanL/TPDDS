package ar.edu.utn.frba.dds.server.routers;

import ar.edu.utn.frba.dds.server.controllers.ColaboracionController;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import io.javalin.Javalin;

public class ColaboracionRouter {
    public static void init(Javalin app){
        app.get("/colaborar", ServiceLocator.instanceOf(ColaboracionController.class)::index);
        app.post("/colaborar/donacionDeDinero", ServiceLocator.instanceOf(ColaboracionController.class)::donacionDeDineroSave);
        app.post("/colaborar/donacionDeVianda", ServiceLocator.instanceOf(ColaboracionController.class)::donacionDeViandaSave);
        app.post("/colaborar/distribucionDeVianda", ServiceLocator.instanceOf(ColaboracionController.class)::distribucionDeViandaSave);
        app.post("/colaborar/registroPersonaVulnerable", ServiceLocator.instanceOf(ColaboracionController.class)::registroPersonaVulnerableSave);

        app.get("/colaborarPersonaJuridica" , ServiceLocator.instanceOf(ColaboracionController.class)::colaborarPersonaJuridicaIndex);
        app.post("/colaborarPersonaJuridica/heladera", ServiceLocator.instanceOf(ColaboracionController.class)::colocacionDeHeladeraSave);
        app.post("/colaborarPersonaJuridica/ofertaProducto", ServiceLocator.instanceOf(ColaboracionController.class)::ofertaProductoSave);

    }
}
