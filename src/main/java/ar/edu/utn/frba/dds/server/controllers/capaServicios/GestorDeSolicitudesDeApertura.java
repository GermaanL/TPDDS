package ar.edu.utn.frba.dds.server.controllers.capaServicios;

import ar.edu.utn.frba.dds.domain.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.heladeras.MotivoApertura;
import ar.edu.utn.frba.dds.domain.heladeras.SolicitudApertura;
import ar.edu.utn.frba.dds.domain.heladeras.SolicitudAperturaDTO;
import ar.edu.utn.frba.dds.domain.viandas.Vianda;
import ar.edu.utn.frba.dds.repositories.SolicitudesDeAperturaRepository;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import ar.edu.utn.frba.dds.utils.LocalDateTimeAdapterJson;
import ar.edu.utn.frba.dds.utils.broker.PublicadorBrocker;
import ar.edu.utn.frba.dds.utils.notificadores.Notificador;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Setter;

import java.time.LocalDateTime;

public class GestorDeSolicitudesDeApertura {

    private static final PublicadorBrokerDEPRECATED publicadorBroker = new PublicadorBrokerDEPRECATED();
    private static final SolicitudesDeAperturaRepository repositorioDeSolicitudesDeApertura = ServiceLocator.instanceOf(SolicitudesDeAperturaRepository.class);
    @Setter
    private static int duracionPermiso = 3;


    public static void solicitarApertura(Heladera heladera, Colaboracion colaboracion, Colaborador colaborador, MotivoApertura motivoApertura, Vianda vianda) {
        SolicitudApertura solicitud = crearSolicitudApertura(heladera, colaborador, motivoApertura, colaboracion);
        repositorioDeSolicitudesDeApertura.guardar(solicitud);
        SolicitudAperturaDTO solicitudDTO = solicitud.convertToDTO(solicitud);
        enviarSolicitud(solicitudDTO);
        Notificador notificador = Notificador.of(colaborador.obtenerInfoDeContacto().getTipo());
        assert notificador != null;
        notificador.notificar(colaborador, "Se ha registrado sus solicitud de apertura de heladera.\nAl momento de usar la tarjeta deberá ingresar el identificador de la solicitud el cual es: " + solicitud.getId());
    }

    public static SolicitudApertura crearSolicitudApertura(Heladera heladera, Colaborador colaborador, MotivoApertura motivoApertura, Colaboracion colaboracion) {
        SolicitudApertura solicitud = new SolicitudApertura();
        solicitud.setHeladeraSolicitada(heladera);
        solicitud.setFechaHoraCreacion(LocalDateTime.now());
        solicitud.setFechaHoraVencimiento(LocalDateTime.now().plusHours(duracionPermiso));
        solicitud.setColaborador(colaborador);
        solicitud.setTarjetaAUsar(colaborador.getTarjeta());
        solicitud.setMotivoApertura(motivoApertura);
        solicitud.setFueRealizada(false);
        solicitud.setColaboracion(colaboracion);

        return solicitud;
    }

    public static void enviarSolicitud(SolicitudAperturaDTO unaSolicitud){
        String topic = "permisosApertura";
        String clientId = "tarjColab" + unaSolicitud.getCodigoIdentificador();

        Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapterJson())
            .create();
        String solicitudJSON = gson.toJson(unaSolicitud);

//        ejemplo de solicitudJSON

//        {
//            "idHeladera": 1,
//            "fechaHoraCreacion": {
//                "date": "2024-11-23",
//                "time": "16:42:00"
//           },
//        "fechaHoraCreacion": {
//                "date": "2024-11-23",
//                "time": "16:42:00"
//           },
//            "codigoIdentificador": "ABCD1234"
//        }


        PublicadorBrocker.publicar(topic, solicitudJSON);
    }




}
