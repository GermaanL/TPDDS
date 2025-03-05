package ar.edu.utn.frba.dds.domain.services.recomendacionPuntosDeColocacion;

import ar.edu.utn.frba.dds.domain.lugares.Coordenada;

import java.io.IOException;
import java.util.List;

public class AdapterServicioDeRecomendacionDePuntos implements IRecomendadorDePuntosAdapter {

    private ServicioDeRecomendacionDePuntos servicioAdaptado;

    @Override
    public List<Coordenada> buscarPuntos(Coordenada coordenadas, Double radio){
        try {
            return servicioAdaptado.obtenerListadosDePuntos(coordenadas.getLatitud(), coordenadas.getLongitud(), radio);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
