package ar.edu.utn.frba.dds.server.controllers;

import ar.edu.utn.frba.dds.domain.calculadores.CalculadorDeColaboraciones;
import ar.edu.utn.frba.dds.domain.calculadores.CalculadorDePuntos;
import ar.edu.utn.frba.dds.domain.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.domain.colaboraciones.FormaDeColaboracion;
import ar.edu.utn.frba.dds.domain.colaboraciones.FrecuenciaDonacion;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesGenerales.DonacionDeDinero;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaHumana.DistribucionDeVianda;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaHumana.DonacionDeVianda;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaHumana.RegistroPersonaVulnerable;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaJuridica.ColocacionHeladera;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaJuridica.ofertas.Oferta;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaJuridica.ofertas.Ofertar;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaJuridica.ofertas.TipoOferta;
import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import ar.edu.utn.frba.dds.domain.colaboradores.TipoPersona;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.heladeras.MotivoApertura;
import ar.edu.utn.frba.dds.domain.personasVulnerables.PersonaVulnerable;
import ar.edu.utn.frba.dds.domain.sesion.Sesion;
import ar.edu.utn.frba.dds.domain.sesion.Usuario;
import ar.edu.utn.frba.dds.domain.tarjetas.EstadoTarjeta;
import ar.edu.utn.frba.dds.domain.tecnicos.VisitaTecnica;
import ar.edu.utn.frba.dds.repositories.*;
import ar.edu.utn.frba.dds.repositories.ColaboracionRepository.ColaboracionesRepository;
import ar.edu.utn.frba.dds.server.controllers.capaServicios.GestorDeSolicitudesDeApertura;
import ar.edu.utn.frba.dds.server.framework.ICrudViewsHandler;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import ar.edu.utn.frba.dds.utils.auth.UsuarioService;
import io.javalin.http.Context;
import ar.edu.utn.frba.dds.domain.viandas.Vianda;
import io.javalin.http.UploadedFile;
import io.micrometer.core.instrument.step.StepMeterRegistry;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ColaboracionController implements ICrudViewsHandler {

    private ColaboracionesRepository repositorioColaboraciones;
    private ColaboradoresRepository repositorioColaboradores;
    private CalculadorDePuntos calculadorDePuntos;
    private HeladerasRepository repositorioHeladeras;
    private FormaDeColaboracionRepository repositorioFormasColaborar;

    public ColaboracionController(ColaboracionesRepository repositorioColaboraciones,
                                  ColaboradoresRepository repositorioColaboradores,
                                  CalculadorDePuntosRepository repositorioCalculadorDePuntos,
                                  HeladerasRepository repositorioHeladeras,
                                  FormaDeColaboracionRepository repositorioFormasColaborar) {
        this.repositorioColaboraciones = repositorioColaboraciones;
        this.repositorioColaboradores = repositorioColaboradores;
        this.calculadorDePuntos = repositorioCalculadorDePuntos.buscarPorId(1L, CalculadorDePuntos.class).get();
        this.repositorioHeladeras = repositorioHeladeras;
        this.repositorioFormasColaborar = repositorioFormasColaborar;
    }

    private void guardarColaborador(Context context, Colaboracion colaboracion) {
        Usuario usuario = ServiceLocator.instanceOf(SesionRepository.class).buscarPorId(Sesion.obtenerUsernameDesdeSesionID(context.cookieStore().get("sesion_id")), Usuario.class).get();
        Colaborador colaborador = repositorioColaboradores.buscarPorUsername(usuario.getUsername()).get();

        colaborador.realizarColaboracion(colaboracion);

        colaborador.calcularPuntos(this.calculadorDePuntos);

        repositorioColaboraciones.guardar(colaboracion);
        repositorioColaboradores.actualizar(colaborador);
    }

    @Override
    public void index(Context context) {

        //TODO Cambiar la clase y ver manejo del tipo de colaboraciones.A la funcion que trae colaboraciones hay que pasarle el id del usuario que
        // sacamos de la galleta

        Map<String, Object> model = new HashMap<>();

        List<Colaboracion> colaboraciones = null;

        String username = Sesion.obtenerUsernameDesdeSesionID(context.cookieStore().get("sesion_id"));

        Optional<Colaborador> posibleColaborador = this.repositorioColaboradores.buscarPorUsername(username);

        Colaborador colaborador = null;

        if(posibleColaborador.isPresent()) {
            colaborador = posibleColaborador.get();
            colaboraciones = colaborador.getColaboracionesRealizadas();
            colaboraciones.stream().filter(c -> c.getFechaAlta()!= null).toList();

            UsuarioService.agregarAtributosDeUsuario(
                    colaborador,
                    username,
                    model
            );

            model.put("heladeras", repositorioHeladeras.buscarTodos(Heladera.class).stream().filter(Heladera::getEstaActiva).toList());

            if(colaborador.getTarjeta() != null && colaborador.getTarjeta().getEstado_actual() == EstadoTarjeta.Autorizada){
                model.put("tieneTarjetaAutorizada", true);
            }

            model.put("colaboraciones", colaboraciones);
            model.put("titulo", "Colaborar");
            model.put("frecuencia", Arrays.stream(FrecuenciaDonacion.values()).map(Enum::name).collect(Collectors.toList()));
            //TODO revisar lo de puntos que pasa si el get no trae nada
            model.put("puntos", colaborador.getPuntosDisponibles());
            context.render("/colaboraciones/colaborar.hbs", model);
        }
        else {
            context.redirect("/login");
        }

    }

    public void donacionDeDineroSave(Context context) {
        DonacionDeDinero donacion = new DonacionDeDinero();
        donacion.setMonto(Integer.parseInt(context.formParam("valor")));
        donacion.setFrecuencia(FrecuenciaDonacion.valueOf(context.formParam("frecuencia")));
        donacion.setFechaAlta(LocalDateTime.now());

        donacion.setTipo(repositorioFormasColaborar.buscarPorNombre("Donacion de Dinero").orElse(null));

        StepMeterRegistry registry = ServiceLocator.instanceOf(StepMeterRegistry.class);
        registry.counter("dds.donacion","status","ok").increment();

        guardarColaborador(context, donacion);

        context.redirect("/main");
    }

    public void donacionDeViandaSave(Context context) {
        DonacionDeVianda donacion = new DonacionDeVianda();
        Vianda vianda = new Vianda();

        vianda.setNombre(context.formParam("comida"));
        vianda.setFechaCaducidad(LocalDate.parse(context.formParam("fechaVencimiento")));
        donacion.setFechaAlta(LocalDateTime.now());

        Optional<Heladera> heladera = repositorioHeladeras.buscarPorId(Long.parseLong(context.formParam("heladeraId")), Heladera.class);

        vianda.setCaloriasEnKcal(Integer.parseInt(context.formParam("calorias")));
        vianda.setPesoEnKg(Double.parseDouble(context.formParam("peso")));

        donacion.setVianda(vianda);
        donacion.setCantidad(Integer.parseInt(context.formParam("cantidad")));
        donacion.setPendiente(true);

        donacion.setTipo(repositorioFormasColaborar.buscarPorNombre("Donacion de Vianda").orElse(null));

        Usuario usuario = ServiceLocator.instanceOf(SesionRepository.class).buscarPorId(Sesion.obtenerUsernameDesdeSesionID(context.cookieStore().get("sesion_id")), Usuario.class).get();
        Colaborador colaborador = repositorioColaboradores.buscarPorUsername(usuario.getUsername()).get();

        colaborador.realizarColaboracion(donacion);

        colaborador.calcularPuntos(this.calculadorDePuntos);

        StepMeterRegistry registry = ServiceLocator.instanceOf(StepMeterRegistry.class);
        registry.counter("dds.donacion","status","ok").increment();

        repositorioColaboraciones.guardar(donacion);
        repositorioColaboradores.actualizar(colaborador);

        GestorDeSolicitudesDeApertura.solicitarApertura(heladera.get(), donacion, colaborador, MotivoApertura.INGRESO_VIANDA, vianda);

        context.redirect("/colaborar");
    }

    public void distribucionDeViandaSave(Context context) {
        DistribucionDeVianda distribucion = new DistribucionDeVianda();

        distribucion.setHeladeraOrigen(this.repositorioHeladeras.buscarPorId(Long.parseLong(context.formParam("heladeraOrigenId")), Heladera.class).get());
        distribucion.setHeladeraDestino(this.repositorioHeladeras.buscarPorId(Long.parseLong(context.formParam("heladeraDestinoId")), Heladera.class).get());
        distribucion.setFechaDeDistribucion(LocalDate.parse(context.formParam("fecha")).atStartOfDay());
        distribucion.setFechaAlta(LocalDateTime.now());
        distribucion.setCantidadDeViandasAMover(Integer.parseInt(context.formParam("cantidadViandas")));
        distribucion.setMovartivo(context.formParam("motivo"));

        distribucion.setTipo(repositorioFormasColaborar.buscarPorNombre("Distribucion de Vianda").orElse(null));

        StepMeterRegistry registry = ServiceLocator.instanceOf(StepMeterRegistry.class);
        registry.counter("dds.donacion","status","ok").increment();

        guardarColaborador(context, distribucion);

        context.redirect("/colaborar");

        context.redirect("/colaborar");
    }

    public void registroPersonaVulnerableSave(Context context){
        PersonaVulnerable personaVulnerable = ServiceLocator.instanceOf(PersonaVulnerableController.class).savePersonaVulnerable(context);

        RegistroPersonaVulnerable colaboracion = new RegistroPersonaVulnerable();

        colaboracion.setFechaAlta(LocalDateTime.now());

        colaboracion.setTarjetaDePersonaVulnerable(null);
        colaboracion.setPersonaRegistrada(personaVulnerable);


        colaboracion.setTipo(repositorioFormasColaborar.buscarPorNombre("Registro Persona Vulnerable").orElse(null));

        StepMeterRegistry registry = ServiceLocator.instanceOf(StepMeterRegistry.class);
        registry.counter("dds.donacion","status","ok").increment();

        guardarColaborador(context, colaboracion);

        context.redirect("/colaborar");
    }

    public void colaborarPersonaJuridicaIndex(Context context) {
        Map<String, Object> model = new HashMap<>();

        List<Colaboracion> colaboraciones = null;

        String usernameLoggeado = Sesion.obtenerUsernameDesdeSesionID(context.cookieStore().get("sesion_id"));

        Optional<Colaborador> colaboradorOpt = this.repositorioColaboradores.buscarPorUsername(usernameLoggeado);

        Colaborador colaborador = null;

        if (colaboradorOpt.isPresent()) {
            colaborador = colaboradorOpt.get();
            colaboraciones = colaborador.getColaboracionesRealizadas();

            List<ColocacionHeladera> colocacionesHeladeras = colaboraciones.stream()
                    .filter(ColocacionHeladera.class::isInstance)
                    .map(ColocacionHeladera.class::cast)
                    .toList();

            // Obtén las heladeras asociadas a las colocaciones
            List<Heladera> heladerasColocadas = colocacionesHeladeras.stream()
                    .map(ColocacionHeladera::getHeladeraColocada)
                    .toList();

            // Busca las visitas técnicas asociadas a las heladeras colocadas
            List<Long> heladerasIdsColocadas = heladerasColocadas.stream()
                    .map(Heladera::getId) // Extrae los IDs de las heladeras colocadas
                    .toList();

            List<VisitaTecnica> visitasTecnicas = ServiceLocator.instanceOf(VisitasTecnicasRepository.class)
                    .buscarTodos(VisitaTecnica.class) // Obtén todas las visitas técnicas
                    .stream()
                    .filter(visita -> heladerasIdsColocadas.contains(visita.getHeladera().getId())) // Compara por IDs
                    .toList();

            UsuarioService.agregarAtributosDeUsuario(
                    colaborador,
                    usernameLoggeado,
                    model
            );

            model.put("colaboraciones", colaboraciones);

            model.put("titulo", "Colaborar para persona juridica");

            model.put("visitasTecnicas", visitasTecnicas);

            model.put("frecuencia", Arrays.stream(FrecuenciaDonacion.values()).map(Enum::name).collect(Collectors.toList()));

            model.put("tipoOferta", Arrays.stream(TipoOferta.values()).map(Enum::name).collect(Collectors.toList()));

            //model.put("heladeras", ServiceLocator.instanceOf(HeladerasRepository.class).buscarTodos(Heladera.class));

            context.render("/colaboraciones/colaborarPersonaJuridica.hbs", model);
        }
        else {
            context.redirect("/login");
        }

    }

    public void colocacionDeHeladeraSave(Context context){
        Heladera heladera = ServiceLocator.instanceOf(HeladeraController.class).saveHeladera(context);

        ColocacionHeladera colaboracion = new ColocacionHeladera();
        colaboracion.setFechaAlta(LocalDateTime.now());
        colaboracion.setHeladeraColocada(heladera);

        colaboracion.setTipo(repositorioFormasColaborar.buscarPorNombre("Colocacion de Heladera").orElse(null));

        StepMeterRegistry registry = ServiceLocator.instanceOf(StepMeterRegistry.class);
        registry.counter("dds.donacion","status","ok").increment();

        guardarColaborador(context, colaboracion);

        context.redirect("/colaborarPersonaJuridica");
    }

    public void ofertaProductoSave(Context context){
        Oferta oferta = new Oferta();

        oferta.setNombre(context.formParam("nombreOferta"));
        oferta.setRubro(context.formParam("rubro"));
        oferta.setPuntosNecesarios(Integer.parseInt(context.formParam("puntosNecesarios")));
        oferta.setStock(Integer.parseInt(context.formParam("stock")));
        oferta.setSinStock(false);
        oferta.setCanjeado(0);
        oferta.setTipoOferta(TipoOferta.valueOf(context.formParam("tipoOferta")));

        UploadedFile uploadedImage = context.uploadedFile("imagen");

        if (uploadedImage != null) {
            //TODO mirar que es lo que anda mal al subir
            String sanitizedFileName = uploadedImage.filename().replaceAll("\\s+", "_");
            oferta.setImagen(sanitizedFileName);
            String imagePath = "/home/ubuntu/2024-tpa-mi-no-grupo-07/uploads/ofertas/" + sanitizedFileName;

            //String imagePath = "uploads/ofertas/" + sanitizedFileName; //para local
            try {
                Files.copy(uploadedImage.content(), Paths.get(imagePath), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ServiceLocator.instanceOf(OfertasRepository.class).guardar(oferta);

        Ofertar colaboracion = new Ofertar();

        colaboracion.setTipo(repositorioFormasColaborar.buscarPorNombre("Ofertar").orElse(null));

        colaboracion.setOferta(oferta);
        colaboracion.setFechaAlta(LocalDateTime.now());

        StepMeterRegistry registry = ServiceLocator.instanceOf(StepMeterRegistry.class);
        registry.counter("dds.donacion","status","ok").increment();

        guardarColaborador(context, colaboracion);

        context.redirect("/colaborarPersonaJuridica");
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


    public void eliminarColaboracionOfertar(Context context) {
        Optional<Ofertar> ofertar = ServiceLocator.instanceOf(ColaboracionesRepository.class).buscarPorId(Long.parseLong(context.pathParam("id")), Ofertar.class);
        ServiceLocator.instanceOf(ColaboradoresRepository.class).eliminar(ofertar.get());

        context.redirect("/misOfertas");
    }
}
