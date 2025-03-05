package ar.edu.utn.frba.dds.server.controllers.capaServicios;

import ar.edu.utn.frba.dds.domain.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaHumana.DistribucionDeVianda;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaHumana.DonacionDeVianda;
import ar.edu.utn.frba.dds.domain.heladeras.AperturaHeladera;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.heladeras.MotivoApertura;
import ar.edu.utn.frba.dds.domain.heladeras.SolicitudApertura;
import ar.edu.utn.frba.dds.domain.tarjetas.Tarjeta;
import ar.edu.utn.frba.dds.domain.tarjetas.UsoDeTarjeta;
import ar.edu.utn.frba.dds.repositories.ColaboracionRepository.ColaboracionesRepository;
import ar.edu.utn.frba.dds.repositories.HeladerasRepository;
import ar.edu.utn.frba.dds.repositories.SolicitudesDeAperturaRepository;
import ar.edu.utn.frba.dds.repositories.TarjetasRepository;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import ar.edu.utn.frba.dds.utils.broker.CallbackExecution;
import ar.edu.utn.frba.dds.utils.broker.SuscriptorBrocker;

import java.time.LocalDateTime;

public class ReceptorDeAperturas {

  private final ColaboracionesRepository colaboracionesRepository = ServiceLocator.instanceOf(ColaboracionesRepository.class);
  private final HeladerasRepository heladerasRepository = ServiceLocator.instanceOf(HeladerasRepository.class);
  private final TarjetasRepository tarjetasRepository = ServiceLocator.instanceOf(TarjetasRepository.class);
  private final SolicitudesDeAperturaRepository solicitudesDeAperturaRepository = ServiceLocator.instanceOf(SolicitudesDeAperturaRepository.class);

  public ReceptorDeAperturas() {

  }

  public void recibirApertura() {
    String topic = "aperturasHeladera";

    CallbackExecution ejecutor = new CallbackExecution() {
      @Override
      public void execute(String message) {
        int idSolicitud = Integer.parseInt(message);

        SolicitudApertura solicitudApertura = solicitudesDeAperturaRepository.buscarPorId(idSolicitud, SolicitudApertura.class).get();
        Colaboracion colaboracion = solicitudApertura.getColaboracion();
        Heladera heladera = solicitudApertura.getHeladeraSolicitada();
        Tarjeta tarjeta = solicitudApertura.getTarjetaAUsar();
        registrarApertura(heladera, colaboracion, tarjeta, LocalDateTime.now(), solicitudApertura);
      }
    };

    SuscriptorBrocker.suscribirse(topic , ejecutor);
  }



  public void registrarApertura(Heladera heladera, Colaboracion colaboracion, Tarjeta tarjeta, LocalDateTime fechaHoraApertura, SolicitudApertura solicitud) {
    AperturaHeladera apertura = new AperturaHeladera();
    apertura.setHeladera(heladera);
    
    apertura.setFechaHoraApertura(fechaHoraApertura);
    apertura.setTarjeta(tarjeta);

    heladerasRepository.guardar(apertura);

    UsoDeTarjeta usoDeTarjeta = new UsoDeTarjeta();
    usoDeTarjeta.setTarjetaUsada(tarjeta);
    usoDeTarjeta.setFechaHora(fechaHoraApertura);
    usoDeTarjeta.setHeladeraUsada(heladera);

    DonacionDeVianda donacionDeVianda = colaboracionesRepository.buscarPorId(colaboracion.getId(), DonacionDeVianda.class).orElse(null);
    DistribucionDeVianda distribucionDeVianda = colaboracionesRepository.buscarPorId(colaboracion.getId(), DistribucionDeVianda.class).orElse(null);

    if(solicitud.getMotivoApertura().equals(MotivoApertura.INGRESO_VIANDA) && donacionDeVianda != null){
      heladera.setCantViandas(heladera.getCantViandas() + donacionDeVianda.getCantidad());
      heladerasRepository.actualizar(heladera);
    }
    //TODO logica de distribucion de viandas


    colaboracion.setPendiente(false);
    colaboracionesRepository.actualizar(colaboracion);

    solicitud.setFueRealizada(true);
    solicitudesDeAperturaRepository.actualizar(solicitud);
    tarjetasRepository.guardar(usoDeTarjeta);

  }

  public static void main(String[] args) {
    ReceptorDeAperturas receptorDeAperturas = new ReceptorDeAperturas();
    receptorDeAperturas.recibirApertura();

  }

}
