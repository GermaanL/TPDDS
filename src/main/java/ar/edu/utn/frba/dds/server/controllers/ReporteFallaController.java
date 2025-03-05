package ar.edu.utn.frba.dds.server.controllers;

import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.incidentes.Alerta;
import ar.edu.utn.frba.dds.domain.incidentes.FallaTecnica;
import ar.edu.utn.frba.dds.domain.incidentes.TipoAlerta;
import ar.edu.utn.frba.dds.domain.incidentes.TipoIncidente;
import ar.edu.utn.frba.dds.domain.sesion.Sesion;
import ar.edu.utn.frba.dds.repositories.*;
import ar.edu.utn.frba.dds.server.framework.ICrudViewsHandler;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import ar.edu.utn.frba.dds.utils.auth.UsuarioService;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Provider;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ReporteFallaController implements ICrudViewsHandler {

    ReporteFallaRepository reporteFallaRepository;
    HeladerasRepository repositorioHeladeras;
    AlertaRepository repositorioAlertas;

    public ReporteFallaController(
            ReporteFallaRepository reporteFallaRepository,
            HeladerasRepository repositorioHeladeras,
            AlertaRepository repositorioAlertas){
        this.reporteFallaRepository = reporteFallaRepository;
        this.repositorioHeladeras = repositorioHeladeras;
        this.repositorioAlertas = repositorioAlertas;
    }

    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        String username = Sesion.obtenerUsernameDesdeSesionID(context.cookieStore().get("sesion_id"));
        Colaborador colaborador = ServiceLocator.instanceOf(ColaboradoresRepository.class).buscarPorUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Colaborador no encontrado"));
        List<Heladera> heladeras = this.repositorioHeladeras.buscarTodos(Heladera.class);
        UsuarioService.agregarAtributosDeUsuario(
                colaborador,
                username,
                model
        );
        model.put("heladeras", heladeras);
        model.put("tipoIncidente", Arrays.stream(TipoIncidente.values()).map(Enum::name).collect(Collectors.toList()));
        model.put("tipoAlerta", Arrays.stream(TipoAlerta.values()).map(Enum::name).collect(Collectors.toList()));
        model.put("titulo", "ReporteFallas");
        context.render("/reporte/reporteFallas.hbs", model);
    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

    }

    @Override
    public void save(Context context) {

        ColaboradoresRepository colaboradores = ServiceLocator.instanceOf(ColaboradoresRepository.class);
        HeladerasRepository heladeras = ServiceLocator.instanceOf(HeladerasRepository.class);

        System.out.print(context.formParam("fechaAlta"));

        LocalDateTime fechaAlta = LocalDateTime.parse(
                context.formParam("fechaAlta"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
        );

        Colaborador colaborador = colaboradores.buscarPorUsername(Sesion.obtenerUsernameDesdeSesionID(context.cookieStore().get("sesion_id")))
                .orElseThrow(() -> new IllegalArgumentException("Colaborador no encontrado"));
        HeladerasRepository repositorioHeladeras = ServiceLocator.instanceOf(HeladerasRepository.class);
        Optional<Heladera> heladeraAfectada = repositorioHeladeras.buscarPorId(
                Long.parseLong(context.formParam("heladeraId")),
                Heladera.class);

        //System.out.print("HASTA ACA SI!!");
        FallaTecnica falla = new FallaTecnica();
        falla.setTipoIncidente(TipoIncidente.FALLA_TECNICA);
        falla.setReportadaPor(colaborador);
        falla.setFechaAlta(fechaAlta);
        falla.setSolucionado(Boolean.FALSE);
        falla.setDescripcion(context.formParam("descripcion"));

        UploadedFile uploadedImage = context.uploadedFile("imagen");

        if (uploadedImage != null) {
            String sanitizedFileName = uploadedImage.filename().replaceAll("\\s+", "_");
            falla.setImagen(sanitizedFileName);
            //String imagePath = "uploads/reportes/" + sanitizedFileName;
            String imagePath = "/home/ubuntu/2024-tpa-mi-no-grupo-07/uploads/reportes/" + sanitizedFileName;

            try {
                Files.copy(uploadedImage.content(), Paths.get(imagePath), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        falla.setHeladeraAfectada(heladeraAfectada.get());

        heladeraAfectada.get().recibirFalla();

        ServiceLocator.instanceOf(TecnicoController.class).reportarFalla(heladeraAfectada.get(), falla);

        // Ahora se guarda en el tecnico con el cascade

        context.redirect("/reporteFallas?=Success");
    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {

    }

    @Override
    public void delete(Context context) {

    }
}
