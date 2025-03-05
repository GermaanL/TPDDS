package ar.edu.utn.frba.dds.domain.services.recomendacionPuntosDeColocacion;

import ar.edu.utn.frba.dds.domain.lugares.Coordenada;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecomendadorDePuntosDeColocacion {

    private IRecomendadorDePuntosAdapter adapter;

    public List<Coordenada> buscarPuntosDeColocacion(Coordenada unPunto, Double unRadioEnKm){
        return this.adapter.buscarPuntos(unPunto, unRadioEnKm);
    }
}
