package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.server.framework.JavalinRenderer;
import ar.edu.utn.frba.dds.server.framework.PrettyProperties;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import ar.edu.utn.frba.dds.server.middlewares.AutenticadorPermisos;
import ar.edu.utn.frba.dds.server.obervabilidad.ApprovalException;
import ar.edu.utn.frba.dds.server.obervabilidad.DDMetricsUtils;
import ar.edu.utn.frba.dds.server.obervabilidad.DataDogSingleton;
import ar.edu.utn.frba.dds.server.obervabilidad.TransferDTO;
import io.javalin.micrometer.MicrometerPlugin;
import ar.edu.utn.frba.dds.server.routers.*;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;
import io.javalin.http.staticfiles.Location;
import io.micrometer.core.instrument.MeterRegistry;

import java.io.IOException;
import java.security.Provider;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import io.micrometer.core.instrument.step.StepMeterRegistry;
import io.reactivex.internal.operators.observable.ObservableDistinctUntilChanged;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Server {
    private static Javalin app = null;

    public static Javalin app() {
        if (app == null)
            throw new RuntimeException("App no inicializada");
        return app;
    }


    public static void init() {
        if (app == null) {

            StepMeterRegistry registry = ServiceLocator.instanceOf(StepMeterRegistry.class);

            Integer port = Integer.parseInt(PrettyProperties.getInstance().propertyFromName("server_port"));
            app = Javalin.create(config()).start(port);

            //AuthMiddleware.apply(app);
            //AppHandlers.applyHandlers(app);

            AutenticadorPermisos.apply(app);
            BasicRouter.init(app);
            HeladeraRouter.init(app);
            ColaboracionRouter.init(app);
            PersonaVulnerableRouter.init(app);
            SesionRouter.init(app);
            OfertasRouter.init(app);
            AdminRouter.init(app);
            ReporteFallaRouter.init(app);
            TarjetasRouter.init(app);
            TecnicoRouter.init(app);

            final var myGauge = registry.gauge("dds.unGauge", new AtomicInteger(0));

            app.get("/number/{number}", ctx -> {
                var number = ctx.pathParamAsClass("number", Integer.class).get();
                myGauge.set(number);
                ctx.result("updated number: " + number.toString());
                System.out.println(myGauge);
                log.info("valor gauge cambiado");
            });

            app.post("/transaction", ctx -> {
                log.debug("procesando transferencia");
                var transferencia = ctx.bodyAsClass( TransferDTO.class);
                try {
                    transferir(transferencia);
                    registry.counter("dds.transferencias","status","ok").increment();
                    log.info("transferencia ok!");
                    ctx.result("ok!");
                } catch  (ApprovalException ex) {
                    log.warn("transferencia no aprobada");
                    registry.counter("dds.transferencias","status","rejected").increment();
                    ctx.result("no aprobada!").status(HttpStatus.NOT_ACCEPTABLE);
                } catch  (Exception ex) {
                    log.error("error en transferencia", ex);
                    registry.counter("dds.transferencias","status","error").increment();
                    ctx.result("error!").status(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            });



            if (Boolean.parseBoolean(PrettyProperties.getInstance().propertyFromName("dev_mode"))) {
                //Initializer.init();
            }
        }
    }

    private static Consumer<JavalinConfig> config() {

        MicrometerPlugin micrometerPlugin = ServiceLocator.instanceOf(MicrometerPlugin.class);

        return config -> {
            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/";
                staticFiles.directory = "/public";
                staticFiles.location = Location.CLASSPATH;
            });

            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/uploads";
                staticFiles.directory = "/home/ubuntu/2024-tpa-mi-no-grupo-07/uploads"; //para produccion
                //staticFiles.directory = "uploads"; //para local
                staticFiles.location = Location.EXTERNAL;
            });

            config.fileRenderer(new JavalinRenderer().register("hbs", (path, model, context) -> {
                Handlebars handlebars = new Handlebars();
                HandlebarsHelpers.registerHelpers(handlebars);
                Template template = null;
                try {
                    template = handlebars.compile(
                            "templates" + path.replace(".hbs", ""));
                    return template.apply(model);
                } catch (IOException e) {
                    e.printStackTrace();
                    context.status(HttpStatus.NOT_FOUND);
                    return "No se encuentra la página indicada...";
                }
            }));

            config.registerPlugin(micrometerPlugin);
        };
    }

    private static void transferir(TransferDTO transferencia) throws ApprovalException  {
        if(transferencia.getDst().equals(transferencia.getSrc())) {
            throw new ApprovalException();
        }
        if(transferencia.getAmount() <= 0) {
            throw new IllegalArgumentException();
        }

    }
}
