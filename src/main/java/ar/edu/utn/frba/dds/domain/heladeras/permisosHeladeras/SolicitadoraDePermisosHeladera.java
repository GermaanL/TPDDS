package ar.edu.utn.frba.dds.domain.heladeras.permisosHeladeras;

import ar.edu.utn.frba.dds.domain.heladeras.SolicitudApertura;

public class SolicitadoraDePermisosHeladera {
  private static SolicitadoraDePermisosHeladera  solicitadoraDePermisosHeladera = null;

  public static SolicitadoraDePermisosHeladera getInstance() {
    if (solicitadoraDePermisosHeladera == null) {
      solicitadoraDePermisosHeladera = new SolicitadoraDePermisosHeladera();
    }
    return solicitadoraDePermisosHeladera;
  }

  public void solicitarApertura(SolicitudApertura solicitud){
    /*
    String mensaje = String.format("%d,%s,%s", solicitud.getIdHeladera(), solicitud.getFechaHoraVencimiento().toString(),solicitud.getCodigoTarjeta());
    publicadorBroker.publicar("solicitudApertura_" + solicitud.getIdHeladera(), "tarjeta_" + solicitud.getCodigoTarjeta(), mensaje);
  */
  }

}
