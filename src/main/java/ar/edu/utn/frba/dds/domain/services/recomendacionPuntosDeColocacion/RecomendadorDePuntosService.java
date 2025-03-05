package ar.edu.utn.frba.dds.domain.services.recomendacionPuntosDeColocacion;

import ar.edu.utn.frba.dds.domain.lugares.Coordenada;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import java.util.List;

public interface RecomendadorDePuntosService {

  @GET("api/ubicaciones")
  Call<List<Coordenada>> puntosRecomendados(@Query("latitud")double lat, @Query("longitud")double lon, @Query("radio")double radio);

  @GET("api/ubicaciones")
  Call<List<Coordenada>> puntosRecomendados(@Query("latitud")double lat);
}
