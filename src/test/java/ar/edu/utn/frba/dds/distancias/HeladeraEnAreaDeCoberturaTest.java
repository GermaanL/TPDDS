package ar.edu.utn.frba.dds.distancias;

import ar.edu.utn.frba.dds.domain.calculadores.CalculadorDeDistancias;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.lugares.AreaDeCobertura;
import ar.edu.utn.frba.dds.domain.lugares.Coordenada;
import ar.edu.utn.frba.dds.domain.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.domain.documentos.TipoDoc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HeladeraEnAreaDeCoberturaTest {
/*
    private Heladera heladeraBsAs;
    private Heladera heladeraMDQ;
    private Heladera heladeraNYC;
    private Tecnico unTecnico;
    private Coordenada coordenadasObelisco;
    private Coordenada coordenadasCasinoMDQ;
    private Coordenada coordenadasTecnico;
    private Coordenada coordenadasTimesSquare;
    private Double distancia;

    @Test
    public void heladeraSeEncuentraEnArea(){
        heladeraBsAs = new Heladera("Heladera Obelisco");
        heladeraMDQ = new Heladera("Heladera Casino MDQ");
        coordenadasObelisco = new Coordenada(-34.6037, -58.3816);
        coordenadasCasinoMDQ = new Coordenada(-38.0009, -57.5387);
        coordenadasTecnico = new Coordenada(-34.5986, -58.4204); // FRBA Medrano
        heladeraBsAs.setCoordenada(coordenadasObelisco);
        heladeraMDQ.setCoordenada(coordenadasCasinoMDQ);
        unTecnico = new Tecnico("Juan", "Perez", TipoDoc.DNI, 12345, "12345-1");
        unTecnico.setAreaDeCobertura(new AreaDeCobertura(coordenadasTecnico, 5.00));
        Assertions.assertTrue(unTecnico.heladeraEstaEnAreaDeCobertura(heladeraBsAs));
    }

    @Test
    public void heladeraNoSeEncuentraEnArea(){
        heladeraBsAs = new Heladera("Heladera Obelisco");
        heladeraMDQ = new Heladera("Heladera Casino MDQ");
        coordenadasObelisco = new Coordenada(-34.6037, -58.3816);
        coordenadasCasinoMDQ = new Coordenada(-38.0009, -57.5387);
        coordenadasTecnico = new Coordenada(-34.5986, -58.4204); // FRBA Medrano
        heladeraBsAs.setCoordenada(coordenadasObelisco);
        heladeraMDQ.setCoordenada(coordenadasCasinoMDQ);
        unTecnico = new Tecnico("Juan", "Perez", TipoDoc.DNI, 12345, "12345-1");
        unTecnico.setAreaDeCobertura(new AreaDeCobertura(coordenadasTecnico, 5.00));
        Assertions.assertFalse(unTecnico.heladeraEstaEnAreaDeCobertura(heladeraMDQ));
    }

    @Test
    public void distanciaEntreTecnicoYHeladera(){
        heladeraBsAs = new Heladera("Heladera Obelisco");
        heladeraMDQ = new Heladera("Heladera Casino MDQ");
        heladeraNYC = new Heladera("Heladera Times Square");
        coordenadasObelisco = new Coordenada(-34.6037, -58.3816);
        coordenadasCasinoMDQ = new Coordenada(-38.0009, -57.5387);
        coordenadasTecnico = new Coordenada(-34.5986, -58.4204); // FRBA Medrano
        coordenadasTimesSquare = new Coordenada(40.7580, -73.9855);
        heladeraBsAs.setCoordenada(coordenadasObelisco);
        heladeraMDQ.setCoordenada(coordenadasCasinoMDQ);
        heladeraNYC.setCoordenada(coordenadasTimesSquare);
        unTecnico = new Tecnico("Juan", "Perez", TipoDoc.DNI, 12345, "12345-1");
        unTecnico.setAreaDeCobertura(new AreaDeCobertura(coordenadasTecnico, 5.00));
        CalculadorDeDistancias calculador = new CalculadorDeDistancias();
        distancia = calculador.calcularDistanciaEntreCoordenadas(unTecnico.getAreaDeCobertura().getCoordenada(), heladeraNYC.getCoordenada());
        System.out.println(distancia);
    }

 */
}
