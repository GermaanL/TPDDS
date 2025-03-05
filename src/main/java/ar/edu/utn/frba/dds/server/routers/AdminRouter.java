package ar.edu.utn.frba.dds.server.routers;

import ar.edu.utn.frba.dds.server.controllers.AdminController;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import io.javalin.Javalin;

public class AdminRouter {
    public static void init(Javalin app) {
        app.get("admin", ServiceLocator.instanceOf(AdminController.class)::index);
        app.post("/admin/upload-csv", ServiceLocator.instanceOf(AdminController.class)::cargaMasiva);
        app.get("/admin/download-latest-report", ServiceLocator.instanceOf(AdminController.class)::descargarReporte);
        app.get("/adminFormularios", ServiceLocator.instanceOf(AdminController.class)::formulariosIndex);
        //app.post("/admin/modificarHeladera", ServiceLocator.instanceOf(AdminController.class)::modificarHeladera);
        //app.post("/admin/eliminarHeladera", ServiceLocator.instanceOf(AdminController.class)::eliminarHeladera);

        app.get("/admin/adminTecnicos", ServiceLocator.instanceOf(AdminController.class)::administrarTecnicos);
        app.post("/tecnicos/cargar", ServiceLocator.instanceOf(AdminController.class)::cargarTecnico);
        app.get("/tecnico/{id}", ServiceLocator.instanceOf(AdminController.class)::verTecnico);
        app.post("/tecnicos/modificar", ServiceLocator.instanceOf(AdminController.class)::modificarTecnico);
        app.post("/tecnicos/eliminar", ServiceLocator.instanceOf(AdminController.class)::eliminarTecnico);

        app.get("/coeficientesColaboracion", ServiceLocator.instanceOf(AdminController.class)::cargarPuntos);

        app.get("/admin/servicioIntegrado", ServiceLocator.instanceOf(AdminController.class)::servicioIntegrado);
    }
}
