package ar.edu.utn.frba.dds.server.controllers;

import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import ar.edu.utn.frba.dds.domain.contactos.MedioDeContacto;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.heladeras.sensores.temperatura.LecturaDeTemperatura;
import ar.edu.utn.frba.dds.domain.heladeras.sensores.temperatura.LecturaTemperaturaDTO;
import ar.edu.utn.frba.dds.domain.lugares.Direccion;
import ar.edu.utn.frba.dds.domain.lugares.Ubicacion;
import ar.edu.utn.frba.dds.domain.services.geocodingService.GeocodingService;
import ar.edu.utn.frba.dds.domain.sesion.Sesion;
import ar.edu.utn.frba.dds.domain.suscripciones.MuchoStock;
import ar.edu.utn.frba.dds.domain.suscripciones.PocoStock;
import ar.edu.utn.frba.dds.domain.suscripciones.Desperfecto;
import ar.edu.utn.frba.dds.domain.suscripciones.Suscripcion;
import ar.edu.utn.frba.dds.domain.tarjetas.EstadoTarjeta;
import ar.edu.utn.frba.dds.domain.tarjetas.Tarjeta;
import ar.edu.utn.frba.dds.domain.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.repositories.*;
import ar.edu.utn.frba.dds.server.framework.ICrudViewsHandler;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import ar.edu.utn.frba.dds.utils.auth.UsuarioService;
import ar.edu.utn.frba.dds.utils.notificadores.Notificador;
import io.javalin.http.Context;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class HeladeraController implements ICrudViewsHandler {

    private HeladerasRepository repositorioHeladeras;
    private ColaboradoresRepository repositorioColaboradores;

    public HeladeraController(HeladerasRepository repositorioHeladeras){
        this.repositorioHeladeras = repositorioHeladeras;
    }

    public HeladeraController(HeladerasRepository repositorioHeladeras, ColaboradoresRepository repositorioColaboradores){
        this.repositorioHeladeras = repositorioHeladeras;
        this.repositorioColaboradores = repositorioColaboradores;
    }

    @Override
    public void index(Context context) {
        List<Heladera> heladeras = this.repositorioHeladeras.buscarTodos(Heladera.class);
        Map<String, Object> model = new HashMap<>();

        String username = Sesion.obtenerUsernameDesdeSesionID(context.cookieStore().get("sesion_id"));

        Optional<Colaborador> colaborador = ServiceLocator.instanceOf(ColaboradoresRepository.class).buscarPorUsername(username);

        if(colaborador.isPresent()) {
            UsuarioService.agregarAtributosDeUsuario(
                    colaborador.get(),
                    username,
                    model
            );
        } else {
            Optional<Tecnico> tecnicoOpt = ServiceLocator.instanceOf(TecnicosRepository.class).buscarPorUsername(username);
            tecnicoOpt.ifPresent(tecnico -> UsuarioService.agregarAtributosDeUsuario(
                    tecnico,
                    username,
                    model
            ));
        }

        model.put("heladeras", heladeras);
        model.put("titulo", "Pagina Principal");
        context.render("/heladeras/mainHeladera.hbs", model);
    }

    public void misHeladeras(Context context) {


        List<Heladera> heladeras = ServiceLocator.instanceOf(HeladerasRepository.class).buscarTodos(Heladera.class);
        Map<String, Object> model = new HashMap<>();

        String username = Sesion.obtenerUsernameDesdeSesionID(context.cookieStore().get("sesion_id"));
        Optional<Colaborador> colaborador = ServiceLocator.instanceOf(ColaboradoresRepository.class).buscarPorUsername(username);

        UsuarioService.agregarAtributosDeUsuario(
                colaborador.get(),
                username,
                model
        );

        // Obtener todas las suscripciones del colaborador
        List<Suscripcion> suscripciones = ServiceLocator
                .instanceOf(SuscripcionesRepository.class)
                .buscarSuscripcionesPorColaborador(colaborador.get());

        System.out.println("Suscripciones:");
        suscripciones.forEach(s -> System.out.println(s));

        // Filtrar las heladeras a las que el usuario está suscrito
        List<Heladera> heladerasUsuario = suscripciones.stream()
                .map(Suscripcion::getHeladera)
                .distinct()  // Evita duplicados en caso de suscripciones múltiples a la misma heladera
                .collect(Collectors.toList());

        System.out.println("Heladeras suscritas por el usuario:");
        heladerasUsuario.forEach(h -> System.out.println(h));
        suscripciones.forEach(s -> System.out.println("Tipo de suscripción: " + s.getTipo()));

        //model.put("heladerasUsuario", heladerasUsuario)
        model.put("titulo", "Mis Heladeras");
        model.put("heladerasUsuario", heladerasUsuario);
        model.put("heladeras", heladeras);
        model.put("suscripciones", suscripciones);
        model.put("mediosDeContacto", Arrays.stream(MedioDeContacto.values()).map(Enum::name).collect(Collectors.toList()));
        context.render("/heladeras/misHeladeras.hbs", model);
    }
    
    public void suscribirHeladera(Context context) {

        boolean checkPocoStock = Objects.equals(context.formParam("checkCantViandasDisponibles"), "on");

        Integer cantidadViandasAvisoStockear = context.formParam("cantViandasDisponibles") != null && !context.formParam("cantViandasDisponibles").isEmpty()
                ? Integer.parseInt(context.formParam("cantViandasDisponibles"))
                : 0;

        boolean checkMuchoStock= Objects.equals(context.formParam("checkCantViandasFaltantes"), "on");

        Integer cantidadViandasAvisoLleno = context.formParam("CantViandasFaltantes") != null && !context.formParam("CantViandasFaltantes").isEmpty()
                ? Integer.parseInt(context.formParam("CantViandasFaltantes"))
                : 0;

        boolean checkDesperfecto = Objects.equals(context.formParam("checkDesperfecto"), "on");

        MedioDeContacto medioDeContacto = MedioDeContacto.valueOf(context.formParam("medioDeContacto"));

        String username = Sesion.obtenerUsernameDesdeSesionID(context.cookieStore().get("sesion_id"));
        Colaborador colaborador = ServiceLocator.instanceOf(ColaboradoresRepository.class).buscarPorUsername(username).get();

        if (checkPocoStock) {
            PocoStock suscripcionPocoStock = new PocoStock();
            suscripcionPocoStock.setViandas_poco_stock(cantidadViandasAvisoStockear);
            suscripcionPocoStock.setMedioDeContacto(medioDeContacto);
            suscripcionPocoStock.setSuscriptor(colaborador);
            suscripcionPocoStock.setNotificador(Notificador.of(medioDeContacto));
            suscripcionPocoStock.setHeladera(ServiceLocator.instanceOf(HeladerasRepository.class).buscarPorId(Long.parseLong(context.formParam("heladeraId")), Heladera.class).get());
            ServiceLocator.instanceOf(SuscripcionesRepository.class).guardar(suscripcionPocoStock);
        }
        if (checkMuchoStock) {
            MuchoStock suscripcionMuchoStock = new MuchoStock();
            suscripcionMuchoStock.setViandas_mucho_stock(cantidadViandasAvisoLleno);
            suscripcionMuchoStock.setMedioDeContacto(medioDeContacto);
            suscripcionMuchoStock.setSuscriptor(colaborador);
            suscripcionMuchoStock.setNotificador(Notificador.of(medioDeContacto));
            suscripcionMuchoStock.setHeladera(ServiceLocator.instanceOf(HeladerasRepository.class).buscarPorId(Long.parseLong(context.formParam("heladeraId")), Heladera.class).get());
            ServiceLocator.instanceOf(SuscripcionesRepository.class).guardar(suscripcionMuchoStock);
        }
        if (checkDesperfecto) {
            Desperfecto suscripcionDesperfecto = new Desperfecto();
            suscripcionDesperfecto.setMedioDeContacto(medioDeContacto);
            suscripcionDesperfecto.setSuscriptor(colaborador);
            suscripcionDesperfecto.setNotificador(Notificador.of(medioDeContacto));
            suscripcionDesperfecto.setHeladera(ServiceLocator.instanceOf(HeladerasRepository.class).buscarPorId(Long.parseLong(context.formParam("heladeraId")), Heladera.class).get());
            ServiceLocator.instanceOf(SuscripcionesRepository.class).guardar(suscripcionDesperfecto);
        }

        context.redirect("/heladeras");
    }

    @Override
    public void save(Context context) {

    }

    public Heladera saveHeladera(Context context){
        Heladera heladera = new Heladera();
        heladera.setNombre(context.formParam("nombre"));

        //Direccion
        Direccion direccion = new Direccion();
        direccion.setCalle(context.formParam("calle"));
        direccion.setAltura(Integer.parseInt(context.formParam("numeroCalle")));
        direccion.setLocalidad(context.formParam("localidad"));
        direccion.setCodigoPostal(Integer.parseInt(context.formParam("cp")));
        direccion.setProvincia(context.formParam("provincia"));

        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setDireccion(direccion);
        ubicacion.setPuntoGeografico(GeocodingService.obtenerCoordenadas(direccion));

        ServiceLocator.instanceOf(UbicacionesRepository.class).guardar(ubicacion);

        heladera.setUbicadaEn(ubicacion);
        heladera.setFechaAlta(LocalDateTime.now());
        heladera.setTemperaturaMinima(Double.parseDouble(context.formParam("tempMin")));
        heladera.setTemperaturaMaxima(Double.parseDouble(context.formParam("tempMax")));
        heladera.setEstaActiva(true);
        heladera.setCapacidadEnUnidades(Integer.parseInt(context.formParam("capacidad")));
        heladera.setFechaColocada(LocalDate.parse(context.formParam("fechaColocacion")));

        heladera.setReceptorDeMovimiento(null);
        heladera.setReceptorDeTemperatura(null);

        ServiceLocator.instanceOf(HeladerasRepository.class).guardar(heladera);

        return heladera;
    }

    @Override
    public void show(Context context) {
        //TODO
    }

    @Override
    public void create(Context context) {
        //TODO
    }



    @Override
    public void edit(Context context) {
        this.repositorioHeladeras.buscarPorId(Long.parseLong(context.pathParam("id")), Heladera.class).ifPresent(heladera -> {
            heladera.setNombre(context.formParam("nombre"));
            this.repositorioHeladeras.actualizar(heladera);
        });
    }

    @Override
    public void update(Context context) {
        //TODO
    }

    @Override
    public void delete(Context context) {
        String id = context.pathParam("id");
        this.repositorioHeladeras.buscarPorId(Long.parseLong(id), Heladera.class).ifPresent(heladera -> {
            this.repositorioHeladeras.eliminar(heladera);
        });
    }

    public void recibirTemperatura(LecturaTemperaturaDTO temperaturaDTO) {
        LecturaDeTemperatura lectura = new LecturaDeTemperatura();
        lectura.setValorEnGrados(Double.parseDouble(temperaturaDTO.getGrados()));
        lectura.setFechaHora(LocalDateTime.now());

        Optional<Heladera> heladera = ServiceLocator.instanceOf(HeladerasRepository.class)
                .buscarPorId(Long.parseLong(temperaturaDTO.getHeladeraId()), Heladera.class);

        if(!heladera.isPresent()){
            System.out.println("No se encontró la heladera con id: " + temperaturaDTO.getHeladeraId());
            return;
        }

        heladera.ifPresent(h -> {
            h.validarTemperatura(lectura);
            lectura.setHeladeraAsociada(heladera.get());

        });

        ServiceLocator.instanceOf(LecturaDeTemperaturaRepository.class).guardar(lectura);
    }
}
