package ar.edu.utn.frba.dds.server.routers;

import ar.edu.utn.frba.dds.server.controllers.ColaboracionController;
import ar.edu.utn.frba.dds.server.controllers.OfertasController;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import io.javalin.Javalin;

public class OfertasRouter {
    public static void init(Javalin app){
        //FALTA TERMINAR
        app.get("/ofertas", ServiceLocator.instanceOf(OfertasController.class)::index);
        app.post("/oferta/{id}", ServiceLocator.instanceOf(OfertasController.class)::ofertaProductoCanjear);
        app.get("/misOfertas", ServiceLocator.instanceOf(OfertasController.class)::misOfertasIndex);
        app.post("/ofertas/{id}/editar", ServiceLocator.instanceOf(OfertasController.class)::editarOferta);
        app.post("/ofertas/{id}/eliminar", ServiceLocator.instanceOf(ColaboracionController.class)::eliminarColaboracionOfertar);
        app.post("/ofertas/{id}/agregar-stock", ServiceLocator.instanceOf(OfertasController.class)::agregarStock);
    }
}
