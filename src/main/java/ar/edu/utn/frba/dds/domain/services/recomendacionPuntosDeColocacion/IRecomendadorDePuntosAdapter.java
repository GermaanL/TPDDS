package ar.edu.utn.frba.dds.domain.services.recomendacionPuntosDeColocacion;

import ar.edu.utn.frba.dds.domain.lugares.Coordenada;
import java.io.IOException;
import java.util.List;

public interface IRecomendadorDePuntosAdapter {
    List<Coordenada> buscarPuntos(Coordenada coordenadas, Double radio);
}
