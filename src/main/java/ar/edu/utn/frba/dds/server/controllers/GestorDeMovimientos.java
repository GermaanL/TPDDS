package ar.edu.utn.frba.dds.server.controllers;

import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.heladeras.sensores.movimiento.MovimientoDetectado;
import ar.edu.utn.frba.dds.repositories.HeladerasRepository;
import lombok.Getter;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class GestorDeMovimientos {

    @Getter private HeladerasRepository repositorioDeHeladeras;
    @Getter private GestorDeIncidentes gestorDeIncidentes;

    public void actualizarMovimientos(){
        List<Heladera> heladeras = this.repositorioDeHeladeras.buscarTodos(Heladera.class);
        heladeras.parallelStream().forEach(h -> {
            Stack<MovimientoDetectado> movimientos = h.getReceptorDeMovimiento().getMovimientosDetectados();
            try {
                MovimientoDetectado movimiento = movimientos.pop();
                movimientos.clear();
                //this.getGestorDeIncidentes().reportarAlerta(h, "ALERTA_MOV");
            } catch (EmptyStackException e) {
                e.getMessage();
                System.out.println("No hay movimientos");
            }
        });
    }
}
