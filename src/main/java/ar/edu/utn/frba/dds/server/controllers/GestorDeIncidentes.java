package ar.edu.utn.frba.dds.server.controllers;

import ar.edu.utn.frba.dds.domain.calculadores.CalculadorDeDistancias;
import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.heladeras.estados.EstadoHeladera;
import ar.edu.utn.frba.dds.domain.heladeras.estados.EstadoHeladeraFactory;
import ar.edu.utn.frba.dds.domain.incidentes.Alerta;
import ar.edu.utn.frba.dds.domain.incidentes.FallaTecnica;
import ar.edu.utn.frba.dds.domain.incidentes.Incidente;
import ar.edu.utn.frba.dds.domain.notificaciones.Notificacion;
import ar.edu.utn.frba.dds.domain.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.repositories.IncidentesRepository;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class GestorDeIncidentes {
/*
    private IncidentesRepository repositorioDeIncidentes;
    private CalculadorDeDistancias calculadorDeDistancias;

    public GestorDeIncidentes(IncidentesRepository repositorioDeIncidentes, CalculadorDeDistancias calculadorDeDistancias) {
        this.repositorioDeIncidentes = repositorioDeIncidentes;
        this.calculadorDeDistancias = calculadorDeDistancias;
    }

    public void reportarAlerta(Heladera unaHeladera, String tipoAlerta){
        Alerta unaAlerta = this.crearAlerta(unaHeladera, tipoAlerta);
        //this.desactivarHeladera(unaHeladera, EstadoHeladeraFactory.createEstado(tipoAlerta));
        Tecnico tecnicoAsignado = this.asignarTecnico(unaHeladera);
        this.notificarTecnico(tecnicoAsignado, unaAlerta);
    }

    public void reportarFallaTecnica(Heladera unaHeladera, Colaborador unColaborador){
        FallaTecnica unaFalla = this.crearFallaTecnica(unaHeladera, unColaborador);
        //unColaborador.reportarFallaTecnica(unaFalla);
        //this.desactivarHeladera(unaHeladera, EstadoHeladeraFactory.createEstado("FALLA_TECNICA"));
        Tecnico tecnicoAsignado = this.asignarTecnico(unaHeladera);
        this.notificarTecnico(tecnicoAsignado, unaFalla);
    }

    public Alerta crearAlerta(Heladera unaHeladera, String tipoAlerta){
        //Alerta alerta = new Alerta(unaHeladera, tipoAlerta)
        Alerta alerta = new Alerta();
        repositorioDeIncidentes.guardar(alerta);
        return alerta;
    }

    public FallaTecnica crearFallaTecnica(Heladera unaHeladera, Colaborador unColaborador){
        //FallaTecnica fallaTecnica = new FallaTecnica(unColaborador, unaHeladera);
        FallaTecnica fallaTecnica = new FallaTecnica();
        repositorioDeIncidentes.guardar(fallaTecnica);
        return fallaTecnica;
    }

    public void desactivarHeladera(Heladera unaHeladera, EstadoHeladera unEstado){
        unaHeladera.setEstado(unEstado);
    }



    public Tecnico asignarTecnico(List<Tecnico> tecnicos, Heladera unaHeladera){
        Tecnico tecnicoMasCercano = this.calculadorDeDistancias.buscarTecnicoMasCercanoAHeladera(tecnicos, unaHeladera);
        return tecnicoMasCercano;
    }

    public void notificarTecnico(Tecnico unTecnico, Incidente unIncidente){

        long unId = unIncidente.getOcurridoEn().getIdHeladera();
        String tipoIncidente = unIncidente.getTipoIncidente();
        String mensaje = "Ha ocurrido un incidente de tipo " + tipoIncidente + " en la heladera n° " + unId;
        Notificacion unaNotificacion = Notificacion.builder()
                                                        .fechaHora(LocalDateTime.now())
                                                        .tipo("TECNICO_INCIDENTE")
                                                        .destinatario(unTecnico)
                                                        .contenido(mensaje)
                                                        .build();
        unTecnico.obtenerNotificador().notificar(unTecnico, unaNotificacion.getContenido());

    }
     */
}
