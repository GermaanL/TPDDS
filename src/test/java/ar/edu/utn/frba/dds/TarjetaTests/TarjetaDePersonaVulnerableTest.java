package ar.edu.utn.frba.dds.TarjetaTests;

import ar.edu.utn.frba.dds.domain.colaboradores.PersonaHumana;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.personasVulnerables.PersonaVulnerable;
import ar.edu.utn.frba.dds.domain.tarjetas.TarjetaDePersonaVulnerable;
import ar.edu.utn.frba.dds.exceptions.NoTieneUsosDisponiblesException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class TarjetaDePersonaVulnerableTest {
    /*
    private TarjetaDePersonaVulnerable tarjeta;
    private PersonaHumana distribuidor;
    private PersonaVulnerable propietario;
    private Heladera heladera;

    @BeforeEach
    public void setUp(){
        //distribuidor = new PersonaHumana("Campus", "Juan", "Gonzalez", "42545222", LocalDate.of(1994, 5, 12));
        propietario = new PersonaVulnerable("Martin", "Demichelis", LocalDate.of(1994, 5, 12), true, null, true, 1, distribuidor);
        tarjeta = new TarjetaDePersonaVulnerable(distribuidor);
        heladera = mock (Heladera.class);
        tarjeta.historialDeUsosDeTarjeta = new ArrayList<>();
        tarjeta.setFechaDeEntrega(LocalDate.now());
        tarjeta.setPropietario(propietario);
    }



    @Test
    public void testTieneUsosDisponibles(){
        //un menor a cargo entonces 6 usos
        tarjeta.agregarUso(new UsoDeTarjetaPersonaVulnerable(tarjeta, LocalDate.now(), heladera));
        tarjeta.agregarUso(new UsoDeTarjetaPersonaVulnerable(tarjeta, LocalDate.now(), heladera));
        tarjeta.agregarUso(new UsoDeTarjetaPersonaVulnerable(tarjeta, LocalDate.now(), heladera));
        tarjeta.agregarUso(new UsoDeTarjetaPersonaVulnerable(tarjeta, LocalDate.now(), heladera));

        assertTrue(tarjeta.tieneUsosDisponibles());
    }

    @Test
    public void testNoTieneUsosDisponibles(){
        //un menor a cargo entonces 6 usos
        tarjeta.agregarUso(new UsoDeTarjetaPersonaVulnerable(tarjeta, LocalDate.now(), heladera));
        tarjeta.agregarUso(new UsoDeTarjetaPersonaVulnerable(tarjeta, LocalDate.now(), heladera));
        tarjeta.agregarUso(new UsoDeTarjetaPersonaVulnerable(tarjeta, LocalDate.now(), heladera));
        tarjeta.agregarUso(new UsoDeTarjetaPersonaVulnerable(tarjeta, LocalDate.now(), heladera));
        tarjeta.agregarUso(new UsoDeTarjetaPersonaVulnerable(tarjeta, LocalDate.now(), heladera));
        tarjeta.agregarUso(new UsoDeTarjetaPersonaVulnerable(tarjeta, LocalDate.now(), heladera));

        assertFalse(tarjeta.tieneUsosDisponibles());
    }

    @Test
    public void testUsarTarjetaSinUsosDisponibles(){
        for(int i = 0; i < 6; i++){
            tarjeta.agregarUso(new UsoDeTarjetaPersonaVulnerable(tarjeta, LocalDate.now(), heladera));
        }
        this.propietario.setTarjeta(tarjeta);
        assertThrowsExactly(NoTieneUsosDisponiblesException.class, ()->propietario.usarTarjeta(heladera));
    }

    @Test
    public void testAgregarUso() {
        UsoDeTarjetaPersonaVulnerable uso = new UsoDeTarjetaPersonaVulnerable(tarjeta, LocalDate.now(), heladera);
        tarjeta.agregarUso(uso);

        assertTrue(tarjeta.getHistorialDeUsosDeTarjeta().contains(uso));
    }
    @Test
    public void testSerEntregada(){
        tarjeta.serEntregada(propietario);
        assertEquals(propietario, tarjeta.getPropietario());
        assertEquals(LocalDate.now(), tarjeta.getFechaDeEntrega());
    }

     */

}
