package ar.edu.utn.frba.dds.server.controllers;

import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import ar.edu.utn.frba.dds.domain.sesion.Sesion;
import ar.edu.utn.frba.dds.domain.tarjetas.EstadoTarjeta;
import ar.edu.utn.frba.dds.domain.tarjetas.SeguimientoTarjeta;
import ar.edu.utn.frba.dds.domain.tarjetas.Tarjeta;
import ar.edu.utn.frba.dds.domain.tarjetas.TarjetaDeColaborador;
import ar.edu.utn.frba.dds.repositories.ColaboradoresRepository;
import ar.edu.utn.frba.dds.repositories.TarjetasRepository;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import ar.edu.utn.frba.dds.utils.GeneradorDeCodigo;
import ar.edu.utn.frba.dds.utils.notificadores.EnviadorMail;
import io.javalin.http.Context;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@AllArgsConstructor @NoArgsConstructor
public class TarjetaController {

  private TarjetasRepository tarjetasRepository;

  public Context solicitarTarjeta(Context context) {
    try{
      String username = Sesion.obtenerUsernameDesdeSesionID(context.cookieStore().get("sesion_id"));

      TarjetaDeColaborador tarjeta = new TarjetaDeColaborador();
      tarjeta.setCodigoIdentificador(GeneradorDeCodigo.generarCodigo());
      tarjeta.setFechaAlta(LocalDateTime.now());
      tarjeta.setEstado_actual(EstadoTarjeta.Asignada);

      Colaborador colaborador = ServiceLocator.instanceOf(ColaboradoresRepository.class).buscarPorUsername(username).get();
      colaborador.setTarjeta(tarjeta);

      SeguimientoTarjeta seguimientoTarjeta = new SeguimientoTarjeta();
      seguimientoTarjeta.setTarjeta(tarjeta);
      seguimientoTarjeta.setEstado(EstadoTarjeta.Asignada);
      seguimientoTarjeta.setFechaHora(LocalDateTime.now());

      ServiceLocator.instanceOf(TarjetasRepository.class).guardar(tarjeta);
      ServiceLocator.instanceOf(TarjetasRepository.class).guardar(seguimientoTarjeta);
      ServiceLocator.instanceOf(ColaboradoresRepository.class).actualizar(colaborador);

    }
    catch (Exception e){
      return context.status(400).json(Map.of("respuesta", "Error al generar la solicitud de tarjeta"));
    }

    return context.status(200).json(Map.of("respuesta", "Solicitud de tarjeta generada correctamente"));

  }

  public void autorizarTarjetaView(Context context){
    context.render("/tarjetas/autorizarTarjeta.hbs");
  }

  public Context autorizarTarjeta(Context context){
    String codigo = context.formParam("codigoTarjeta");
    Optional<Tarjeta> posibleTarjeta = tarjetasRepository.buscarPorCodigo(codigo);

    if (posibleTarjeta.isEmpty()){
      return context.status(400).json(Map.of("respuesta", "El codigo de tarjeta no corresponde con una tarjeta ingresada"));
    }

    Tarjeta tarjeta = posibleTarjeta.get();
    tarjeta.setEstado_actual(EstadoTarjeta.Autorizada);
    tarjeta.setFechaEntrega(LocalDate.now());
    tarjetasRepository.actualizar(tarjeta);

    SeguimientoTarjeta seguimientoTarjeta = new SeguimientoTarjeta();
    seguimientoTarjeta.setTarjeta(tarjeta);
    seguimientoTarjeta.setEstado(EstadoTarjeta.Autorizada);
    seguimientoTarjeta.setFechaHora(LocalDateTime.now());
    tarjetasRepository.guardar(seguimientoTarjeta);

    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.submit(() -> {
      /*
      try {
        //TODO que lo haga el adapeter
        EnviadorMail.enviarMail("menesjuanma2310@gmail.com", "Asunto", "Mensaje del correo");
      }
      catch (MessagingException e) {
        throw new RuntimeException(e);
      }

       */
    });

    return context.status(200);

  }

  public Context despacharTarjeta(Context context){
    String id = context.pathParam("id");
    Optional<Tarjeta> posibleTarjeta = tarjetasRepository.buscarPorId(Long.parseLong(id), Tarjeta.class);

    //TODO - CHEQUEAR SI NO EXISTE
    Tarjeta tarjeta = posibleTarjeta.get();
    tarjeta.setEstado_actual(EstadoTarjeta.Enviada);

    SeguimientoTarjeta seguimientoTarjeta = new SeguimientoTarjeta();
    seguimientoTarjeta.setTarjeta(tarjeta);
    seguimientoTarjeta.setEstado(EstadoTarjeta.Enviada);
    seguimientoTarjeta.setFechaHora(LocalDateTime.now());

    tarjetasRepository.actualizar(tarjeta);
    tarjetasRepository.guardar(seguimientoTarjeta);

    return context.status(200);
  }
}
