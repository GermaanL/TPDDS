package ar.edu.utn.frba.dds.domain.calculadores;

import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.lugares.Coordenada;
import ar.edu.utn.frba.dds.domain.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.exceptions.NoHayTecnicosCercanosException;
import ar.edu.utn.frba.dds.repositories.TecnicosRepository;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.GeodeticCalculator;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;

import java.util.List;

public class CalculadorDeDistancias {

    public Double calcularDistanciaEntreCoordenadas(Coordenada c1, Coordenada c2){
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);
        Coordinate inicio = new Coordinate(c1.getLongitud(), c1.getLatitud());
        Coordinate destino = new Coordinate(c2.getLongitud(), c2.getLatitud());

        GeodeticCalculator calc = new GeodeticCalculator();
        calc.setStartingGeographicPoint(inicio.x, inicio.y);
        calc.setDestinationGeographicPoint(destino.x, destino.y);

        return calc.getOrthodromicDistance() / 1000.0;
    }

    public Tecnico buscarTecnicoMasCercanoAHeladera (List<Tecnico> todosLosTecnicos, Heladera unaHeladera){
        Tecnico tecnicoMasCercano = null;
        Double distanciaMinima = Double.MAX_VALUE;

        for(Tecnico tecnico : todosLosTecnicos) {
            Double distancia = calcularDistanciaEntreCoordenadas(tecnico.getAreaDeCobertura().getCoordenada(), unaHeladera.getUbicadaEn().getPuntoGeografico());
            if(distancia <= tecnico.getAreaDeCobertura().getRadioCoberturaEnKm() && distancia < distanciaMinima){
                distanciaMinima = distancia;
                tecnicoMasCercano = tecnico;
            }
        }
        if(tecnicoMasCercano == null) throw new NoHayTecnicosCercanosException ("¡No se pudo encontrar ningún técnico disponible!");
        return tecnicoMasCercano;
    }
}
