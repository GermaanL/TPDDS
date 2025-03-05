package ar.edu.utn.frba.dds.server.controllers;

import ar.edu.utn.frba.dds.domain.calculadores.CalculadorDeDistancias;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.notificaciones.Notificacion;
import ar.edu.utn.frba.dds.domain.suscripciones.ISuscriptorDeHeladeras;
import ar.edu.utn.frba.dds.domain.suscripciones.TipoSuscripcion;
import ar.edu.utn.frba.dds.repositories.NotificacionesRepository;
import ar.edu.utn.frba.dds.server.framework.ICrudViewsHandler;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import io.javalin.http.Context;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SuscripcionesController implements ICrudViewsHandler {

    private NotificacionesRepository repositorioDeNotificaciones;
    private CalculadorDeDistancias calculadorDeDistancias;

    //TODO Cambiar al service Locator
    public SuscripcionesController(NotificacionesRepository repositorioDeNotificaciones, CalculadorDeDistancias calculadorDeDistancias) {
        this.repositorioDeNotificaciones = repositorioDeNotificaciones;
        this.calculadorDeDistancias = calculadorDeDistancias;
    }
    //Desde el form viene la heladeraId, userId, la verificacion a cada tipo de suscripcion (si la quiere), el parametro configurable y el medio de contacto
    @Override
    public void save(Context context) {
        //IF para los tipos de suscripcion que quiere el usuario
        //Creo la suscripcion con los datos que vienen del context
        //Verificar que tenga el tipo de noti solicitado
        //Guardo la suscripcion
        context.redirect("/heladeras");
    }

    //Quien guarda los suscriptores? La heladera, una tabla intermedia o algun hash map


    /*
    public void notificarSuscriptoresDe(Heladera unaHeladera, TipoSuscripcion unTipoDeSuscripcion){
        List<ISuscriptorDeHeladeras> suscriptores = this.obtenerSuscriptoresDe(unaHeladera, unTipoDeSuscripcion);
        suscriptores.parallelStream().forEach(s -> {
            Notificacion unaNotificacion = this.crearNotificacionDeSuscripcion(s, unaHeladera, unTipoDeSuscripcion);
            repositorioDeNotificaciones.guardar(unaNotificacion);
            s.obtenerNotificador().notificar(s, unaNotificacion.getContenido());
        });
    }

    */
    /*
    public List<ISuscriptorDeHeladeras> obtenerSuscriptoresDe(Heladera unaHeladera, TipoSuscripcion unTipoDeSuscripcion){
        Stream<ISuscriptorDeHeladeras> suscriptores = unaHeladera
                .getSuscriptores()
                .stream();
        Stream<ISuscriptorDeHeladeras> suscriptoresFiltrados =
                switch(unTipoDeSuscripcion){
                    case HELADERA_POCO_STOCK -> suscriptores.filter(s -> s.correspondeNotificarPocoStockDe(unaHeladera));
                    case HELADERA_LLENANDOSE -> suscriptores.filter(s -> s.correspondeNotificarMuchoStockDe (unaHeladera));
                    case HELADERA_INACTIVA -> suscriptores.filter(s -> s.correspondeNotificarInactividadDe(unaHeladera));
        };
        return suscriptoresFiltrados.collect(Collectors.toList());
    }

 */

    public Notificacion crearNotificacionDeSuscripcion(ISuscriptorDeHeladeras unSuscriptor, Heladera unaHeladera, TipoSuscripcion unTipoDeSuscripcion){
        long unId = unaHeladera.getId();
        String mensaje = switch(unTipoDeSuscripcion){
            case HELADERA_POCO_STOCK -> "¡Quedan pocas viandas en la heladera n° " + unId + "!";
            case HELADERA_LLENANDOSE -> "¡La heladera n° " + unId + "está próxima a llenarse!";
            case HELADERA_INACTIVA -> "La heladera n° " + unId + "se encuentra fuera de servicio.";
        };
        Notificacion.NotificacionBuilder unaNotificacionBuilder = Notificacion.builder()
                .fechaHora(LocalDateTime.now())
                .destinatario(unSuscriptor)
                .contenido(mensaje);
        Notificacion unaNotificacion = switch(unTipoDeSuscripcion){
            case HELADERA_POCO_STOCK -> unaNotificacionBuilder.tipo("HELADERA_POCO_STOCK").build();
            case HELADERA_LLENANDOSE -> unaNotificacionBuilder.tipo("HELADERA_LLENANDOSE").build();
            case HELADERA_INACTIVA -> unaNotificacionBuilder.tipo("HELADERA_INACTIVA").build();
        };
        return unaNotificacion;
    }

    @Override
    public void index(Context context) {

    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

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
