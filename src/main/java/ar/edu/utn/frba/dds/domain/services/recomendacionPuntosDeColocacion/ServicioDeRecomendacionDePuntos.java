package ar.edu.utn.frba.dds.domain.services.recomendacionPuntosDeColocacion;

import ar.edu.utn.frba.dds.domain.lugares.Coordenada;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;
import java.util.List;

public class ServicioDeRecomendacionDePuntos {
  private static ServicioDeRecomendacionDePuntos instance = null;
  private static final String urlBaseApi = "https://c3141600-44fd-407c-9f63-b665ebf38dfb.mock.pstmn.io"; //https://app.getpostman.com/join-team?invite_code=49d5fccf4dc71027599d6278b775d941&target_code=787d6b8260a3ea92f7b84c73e54abb34 URL del workspace de Postman donde fue mockeada la API
  private Retrofit retrofit;

  private ServicioDeRecomendacionDePuntos(){
    this.retrofit = new Retrofit.Builder().baseUrl(urlBaseApi).addConverterFactory(GsonConverterFactory.create()).build();
  }
  public static ServicioDeRecomendacionDePuntos getInstance(){
    if (instance == null){
      instance = new ServicioDeRecomendacionDePuntos();
    }
    return instance;
  }

  public List<Coordenada> obtenerListadosDePuntos(Double latitud, Double longitud, Double radio) throws IOException {
    RecomendadorDePuntosService recomendadorDePuntosService = this.retrofit.create(RecomendadorDePuntosService.class);
    Call<List<Coordenada>> requetestPuntos = recomendadorDePuntosService.puntosRecomendados(latitud, longitud,radio);
    Response<List<Coordenada>> responseListadoDePuntos = requetestPuntos.execute();
    return responseListadoDePuntos.body();
  }

  public List<Coordenada> obtenerListadosDePuntos(Double latitud) {
    RecomendadorDePuntosService recomendadorDePuntosService = this.retrofit.create(RecomendadorDePuntosService.class);
    Call<List<Coordenada>> requetestPuntos = recomendadorDePuntosService.puntosRecomendados(latitud);
    Response<List<Coordenada>> responseListadoDePuntos = null;
    try {
      responseListadoDePuntos = requetestPuntos.execute();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return responseListadoDePuntos.body();
  }
}
