package ar.edu.utn.frba.dds.CalculadorTests;

import static org.mockito.Mockito.mock;

public class CalculadorTest {
/*
    private PersonaHumana colaboradorPedro;
    private PersonaVulnerable vulnerableAna;
    private PersonaVulnerable vulnerableJose;
    private PersonaVulnerable vulnerableMario;
    private TarjetaDePersonaVulnerable tarjetaAna;
    private TarjetaDePersonaVulnerable tarjetaJose;
    private TarjetaDePersonaVulnerable tarjetaMario;
    private CalculadorDePuntos calculador;
    private OfertarProducto prodCanjeUno;
    private OfertarProducto prodCanjeDos;
    private OfertarProducto prodCanjeTres;
    private Vianda viandaUno;
    private Vianda viandaDos;
    private Heladera heladera;

    @BeforeEach
    public void setUp(){
        calculador = new CalculadorDePuntos(0.5, 1.0, 1.5, 2.0, 5.0);
        colaboradorPedro = new PersonaHumana(mock (Direccion.class), "Pedro", "Pérez", "12345");
        vulnerableAna = new PersonaVulnerable("Ana", "López", LocalDate.of(1994, 5, 12), true, null, true, 1, colaboradorPedro);
        vulnerableJose = new PersonaVulnerable("Jose", "López", LocalDate.of(1994, 5, 12), true, null, true, 1, colaboradorPedro);
        vulnerableMario = new PersonaVulnerable("Mario", "López", LocalDate.of(1994, 5, 12), true, null, true, 1, colaboradorPedro);
        tarjetaAna = new TarjetaDePersonaVulnerable(colaboradorPedro);
        tarjetaJose = new TarjetaDePersonaVulnerable(colaboradorPedro);
        tarjetaMario = new TarjetaDePersonaVulnerable(colaboradorPedro);
        // vulnerableAna.setTarjeta(tarjetaAna);
        // vulnerableJose.setTarjeta(tarjetaJose);
        // vulnerableMario.setTarjeta(tarjetaMario);
        colaboradorPedro.setCalculadorDePuntos(calculador);
        prodCanjeUno = new OfertarProducto(new Producto(), "", 50);
        viandaUno = new Vianda();
        viandaDos = new Vianda();
        heladera = mock (Heladera.class);
        viandaUno.setHeladera(heladera);
        viandaDos.setHeladera(heladera);
    }

    @Test
    public void testPuntosDonacionDinero() throws IOException { // Funciona
        DonacionDeDinero donacionDineroUno = new DonacionDeDinero(450);
        DonacionDeDinero donacionDineroDos = new DonacionDeDinero(100);
        DonacionDeDinero donacionDineroTres = new DonacionDeDinero(240);
        colaboradorPedro.agregarContribucionPosible(DonacionDeDinero.class);
        colaboradorPedro.realizarColaboracion(donacionDineroUno);
        colaboradorPedro.realizarColaboracion(donacionDineroDos);
        colaboradorPedro.realizarColaboracion(donacionDineroTres);
        double puntosAcumulados = colaboradorPedro.getCalculadorDePuntos().calcularPuntos(colaboradorPedro);
        Assertions.assertEquals(puntosAcumulados, 395.0, "Los puntos acumulados fueron 395.0");
    }

    @Test
    public void testPuntosTarjetasRepartidas() throws IOException { // Funciona
        RegistroPersonaVulnerable registroPersonaVulnerableUno = new RegistroPersonaVulnerable(vulnerableAna, tarjetaAna);
        RegistroPersonaVulnerable registroPersonaVulnerableDos = new RegistroPersonaVulnerable(vulnerableJose, tarjetaJose);
        RegistroPersonaVulnerable registroPersonaVulnerableTres = new RegistroPersonaVulnerable(vulnerableMario, tarjetaMario);
        colaboradorPedro.agregarContribucionPosible(RegistroPersonaVulnerable.class);
        colaboradorPedro.realizarColaboracion(registroPersonaVulnerableUno);
        colaboradorPedro.realizarColaboracion(registroPersonaVulnerableDos);
        colaboradorPedro.realizarColaboracion(registroPersonaVulnerableTres);
        double puntosAcumulados = colaboradorPedro.getCalculadorDePuntos().calcularPuntos(colaboradorPedro);
        Assertions.assertEquals(puntosAcumulados, 6.0, "Los puntos acumulados fueron 6.0");
    }

    @Test
    public void testDonacionViandas() throws IOException {
        DonacionDeVianda donacionDeViandaUno = new DonacionDeVianda();
        donacionDeViandaUno.agregarVianda(viandaUno);
        DonacionDeVianda donacionDeViandaDos = new DonacionDeVianda();
        donacionDeViandaDos.agregarVianda(viandaDos);
        colaboradorPedro.agregarContribucionPosible(DonacionDeVianda.class);
        colaboradorPedro.realizarColaboracion(donacionDeViandaUno);
        colaboradorPedro.realizarColaboracion(donacionDeViandaDos);
        //int donaciones = colaboradorPedro.donacionesDeViandasHechas().size();
        //int viandasDonadas = colaboradorPedro.viandasDonadas();
        double puntosAcumulados = colaboradorPedro.getCalculadorDePuntos().calcularPuntos(colaboradorPedro);
        Assertions.assertEquals(puntosAcumulados, 3.0, "Los puntos acumulados fueron 6.0");
    }

    @Test
    public void testCanjeoPuntos() throws IOException {
        DonacionDeDinero donacionDineroUno = new DonacionDeDinero(1000);
        RegistroPersonaVulnerable registroPersonaVulnerableUno = new RegistroPersonaVulnerable(vulnerableAna, tarjetaAna);
        colaboradorPedro.agregarContribucionPosible(DonacionDeDinero.class);
        colaboradorPedro.agregarContribucionPosible(RegistroPersonaVulnerable.class);
        colaboradorPedro.realizarColaboracion(donacionDineroUno);
        colaboradorPedro.realizarColaboracion(registroPersonaVulnerableUno);
        double puntosAcumulados = colaboradorPedro.getCalculadorDePuntos().calcularPuntos(colaboradorPedro);
        colaboradorPedro.canjearPuntos(prodCanjeUno);
        double puntosCanjeados = colaboradorPedro.getPuntosCanjeados();
        Assertions.assertEquals(puntosCanjeados, 50.0, "Los puntos canjeados fueron 50.0");
    }

 */
}


