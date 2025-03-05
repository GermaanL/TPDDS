package ar.edu.utn.frba.dds.server.routers;

import io.javalin.Javalin;

public class BasicRouter {
    public static void init(Javalin app){
        app.get("/", ctx -> ctx.redirect("/main"));

        app.get("/saludo", ctx->ctx.result("Hola Mundo sos: "+ ctx.queryParam("nombre")));

        app.get("/saludo-para/santiago", ctx->ctx.result("mogolico"));

        app.get("/saludo-para/{nombre}", ctx -> ctx.result("Hola " + ctx.pathParam("nombre")));

    }
}
