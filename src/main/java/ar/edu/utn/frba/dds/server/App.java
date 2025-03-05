package ar.edu.utn.frba.dds.server;

import static java.lang.Thread.sleep;

import ar.edu.utn.frba.dds.domain.heladeras.sensores.temperatura.ReceptorDeTemperatura;
import ar.edu.utn.frba.dds.server.controllers.capaServicios.ReceptorDeAperturas;

import lombok.SneakyThrows;

public class App {
    @SneakyThrows
    public static void main(String[] args) {

        Server.init();

        new Thread(() -> {
            ReceptorDeTemperatura receptorDeTemperatura = new ReceptorDeTemperatura();

            receptorDeTemperatura.recibirTemperatura();
        }).start();

        Thread.sleep(1000);
        
        new Thread(() -> {
            ReceptorDeAperturas receptorDeAperturas = new ReceptorDeAperturas();

            receptorDeAperturas.recibirApertura();
        }).start();
        
    }
}
