package ar.edu.utn.frba.dds.utils.cargaMasiva;

import ar.edu.utn.frba.dds.domain.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.domain.colaboraciones.FrecuenciaDonacion;
import ar.edu.utn.frba.dds.domain.colaboraciones.IContribuible;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesGenerales.DonacionDeDinero;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaHumana.DistribucionDeVianda;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaHumana.DonacionDeVianda;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaHumana.RegistroPersonaVulnerable;
import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import ar.edu.utn.frba.dds.domain.viandas.Vianda;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ColaboracionFactory{

  public static Colaboracion crearInstacia(String nombreClase, LocalDateTime fecha, int cantidad){

    switch (nombreClase) {
      case "DINERO" :
        DonacionDeDinero donacionDeDinero = new DonacionDeDinero( cantidad, FrecuenciaDonacion.UNICA);
        return donacionDeDinero;
      case "DONACION_VIANDAS":
        DonacionDeVianda donacionDeVianda = new DonacionDeVianda();
        donacionDeVianda.setFechaAlta(fecha);
        donacionDeVianda.setVianda(new Vianda());
        donacionDeVianda.setCantidad(cantidad);
        return donacionDeVianda;
      case "REDISTRIBUCION_VIANDAS":
        DistribucionDeVianda distribucionDeVianda = new DistribucionDeVianda();
        distribucionDeVianda.setCantidadDeViandasAMover(cantidad);
        return distribucionDeVianda;
      case "ENTREGA_TARJETAS":
        RegistroPersonaVulnerable registroPersona = new RegistroPersonaVulnerable();
        //cantidad?
        return registroPersona;
      default:
        return null;
    }
  }
}
