package ar.edu.utn.frba.dds.domain.services.medicionDeTemperaturaHeladera;

import ar.edu.utn.frba.dds.domain.lugares.Coordenada;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import java.util.List;

public interface MedidorTemperaturaHeladeraService {

  @GET("api/heladera/temperatura")
  Call<TemperaturaDeHeladera> temperaturaHeladera(@Query("latitud")double lat, @Query("longitud")double lon);

}
