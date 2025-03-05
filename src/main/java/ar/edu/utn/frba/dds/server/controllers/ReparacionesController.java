package ar.edu.utn.frba.dds.server.controllers;

import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import ar.edu.utn.frba.dds.domain.incidentes.FallaTecnica;
import ar.edu.utn.frba.dds.domain.incidentes.Incidente;
import ar.edu.utn.frba.dds.domain.sesion.Sesion;
import ar.edu.utn.frba.dds.domain.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.domain.tecnicos.VisitaTecnica;
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
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ReparacionesController implements ICrudViewsHandler {

    private VisitasTecnicasRepository visitasTecnicasRepository;
    private TecnicosRepository tecnicosRepository;
    private IncidentesRepository incidentesRepository;

    public ReparacionesController(VisitasTecnicasRepository reparacionesRepository,
                                  TecnicosRepository tecnicosRepository,
                                  IncidentesRepository incidentesRepository) {
        this.visitasTecnicasRepository = reparacionesRepository;
        this.tecnicosRepository = tecnicosRepository;
        this.incidentesRepository = incidentesRepository;
    }
    @Override
    public void index(Context context) {

        Map<String, Object> model = new HashMap<>();
        String username = Sesion.obtenerUsernameDesdeSesionID(context.cookieStore().get("sesion_id"));
        Optional<Tecnico> tecnico = tecnicosRepository.buscarPorUsername(username);

        List<Incidente> incidentesAsignados = tecnico.map(Tecnico::getIncidentesAsignados).orElse(null);
        List<VisitaTecnica> visitas = tecnico
                .map(t -> visitasTecnicasRepository.buscarTodos(VisitaTecnica.class)
                        .stream()
                        .filter(visita -> visita.getTecnico().getId().equals(t.getId()))
                        .toList())
                .orElse(Collections.emptyList());

        if(tecnico.isPresent()) {
            UsuarioService.agregarAtributosDeUsuario(
                    tecnico.get(),
                    username,
                    model
            );
        } else { // es admin
            UsuarioService.agregarAtributosDeUsuario(
                    ServiceLocator.instanceOf(ColaboradoresRepository.class).buscarPorUsername(username).get(),
                    username,
                    model
            );
        }

        model.put("titulo", "Mis Reparaciones");
        model.put("incidentesAsignados", incidentesAsignados);
        model.put("visitasRealizadas", visitas);
        context.render("/reparaciones/misReparaciones.hbs", model);
    }

    public void realizarVisita(Context context) {
        // Obtener datos del formulario
        String fechaVisitaStr = context.formParam("fechaVisita");
        String observaciones = context.formParam("observaciones");
        String incidenteSolucionadoStr = context.formParam("incidenteSolucionado");
        Boolean incidenteSolucionado = Boolean.parseBoolean(incidenteSolucionadoStr);
        //String foto = context.formParam("foto");

        // Convertir fecha y hora de String a LocalDateTime
        LocalDateTime fechaVisita = LocalDateTime.parse(fechaVisitaStr);

        // Obtener la sesión del técnico que realiza la visita
        String username = Sesion.obtenerUsernameDesdeSesionID(context.cookieStore().get("sesion_id"));
        Tecnico tecnico = tecnicosRepository.buscarPorUsername(username)
                .orElseThrow(() -> new IllegalStateException("Técnico no encontrado"));

        // Obtener incidente relacionado
        Long incidenteId = Long.parseLong(context.formParam("id_incidente"));

        Incidente incidente = incidentesRepository.buscarPorId(incidenteId, Incidente.class)
                .orElseThrow(() -> new IllegalArgumentException("Incidente no encontrado"));

        // Crear instancia de VisitaTecnica
        VisitaTecnica visitaTecnica = new VisitaTecnica();
        visitaTecnica.setFechaVisita(fechaVisita);
        visitaTecnica.setObservaciones(observaciones);
        visitaTecnica.setIncidenteSolucionado(incidenteSolucionado);

        UploadedFile uploadedImage = context.uploadedFile("imagen");

        if (uploadedImage != null) {
            String sanitizedFileName = uploadedImage.filename().replaceAll("\\s+", "_");
            visitaTecnica.setFoto(sanitizedFileName);
            //String imagePath = "uploads/visitasTecnicas/" + sanitizedFileName;
            String imagePath = "/home/ubuntu/2024-tpa-mi-no-grupo-07/uploads/visitasTecnicas/" + sanitizedFileName;

            try {
                Files.copy(uploadedImage.content(), Paths.get(imagePath), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        visitaTecnica.setTecnico(tecnico);
        visitaTecnica.setHeladera(incidente.getHeladeraAfectada());

        // Actualizar el estado del incidente si está solucionado
        if (incidenteSolucionado) {
            incidente.setSolucionado(true);
            incidente.setFechaSolucion(fechaVisita);
            incidente.getHeladeraAfectada().serReparada();
            this.incidentesRepository.guardar(incidente);
        }

        visitaTecnica.setCausa(incidente);

        this.visitasTecnicasRepository.guardar(visitaTecnica);

        // Redirigir a la página de detalles del incidente o a otra página
        context.redirect("/reparaciones/falla/" + incidenteId);
    }


    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

    }

    @Override
    public void save(Context context) {

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

    public void mostrarFalla(Context context) {
        Long idIncidente = Long.parseLong(context.pathParam("id"));

        // System.out.print("---------------------ID INCIDENTE: " + idIncidente + "\n");
        // Buscar el incidente en el repositorio (puedes ajustar según tu lógica)
        Incidente incidente = this.incidentesRepository.buscarPorId(idIncidente, Incidente.class)
                .orElseThrow(() -> new IllegalArgumentException("Incidente no encontrado"));



        // Agregar detalles al modelo
        Map<String, Object> model = new HashMap<>();

        String username = Sesion.obtenerUsernameDesdeSesionID(context.cookieStore().get("sesion_id"));

        Optional<Tecnico> tecnicoOpt = ServiceLocator.instanceOf(TecnicosRepository.class).buscarPorUsername(username);
        tecnicoOpt.ifPresent(tecnico -> UsuarioService.agregarAtributosDeUsuario(
                tecnico,
                username,
                model
        ));

        model.put("incidente", incidente);

        // Renderizar la vista falla.hbs
        context.render("/reparaciones/falla.hbs", model);
    }

    public void verReparaciones(Context context) {

        Map<String, Object> model = new HashMap<>();

        String username = Sesion.obtenerUsernameDesdeSesionID(context.cookieStore().get("sesion_id"));

        Colaborador colaborador = ServiceLocator.instanceOf(ColaboradoresRepository.class).buscarPorUsername(username).get();

        UsuarioService.agregarAtributosDeUsuario(
                colaborador,
                username,
                model
        );

        List<VisitaTecnica> visitas = visitasTecnicasRepository.buscarTodos(VisitaTecnica.class);
        System.out.print("VISITAS ----------------- \n" + visitas + "\n");

        model.put("visitasTecnicas", visitas);
        model.put("titulo", "Reparaciones Realizadas");
        context.render("/reparaciones/reparacionesRealizadas.hbs", model);

    }
}
