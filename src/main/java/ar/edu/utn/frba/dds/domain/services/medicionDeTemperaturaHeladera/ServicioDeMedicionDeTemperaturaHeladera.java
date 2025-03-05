package ar.edu.utn.frba.dds.domain.services.medicionDeTemperaturaHeladera;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;


public class ServicioDeMedicionDeTemperaturaHeladera {

  private static ServicioDeMedicionDeTemperaturaHeladera instance = null;
  private static final String urlBaseApi = "https://44de1d14-b1d9-448f-a703-a37d3af07c3c.mock.pstmn.io";
  private Retrofit retrofit;

  private ServicioDeMedicionDeTemperaturaHeladera(){
    this.retrofit = new Retrofit.Builder().baseUrl(urlBaseApi).addConverterFactory(GsonConverterFactory.create()).build();
  }
  public static ServicioDeMedicionDeTemperaturaHeladera getInstance(){
    if (instance == null){
      instance = new ServicioDeMedicionDeTemperaturaHeladera();

    }
    return instance;
  }

  public TemperaturaDeHeladera obtenerTemperatura(Double latitud, Double longitud) throws IOException {
    MedidorTemperaturaHeladeraService medidorTemperaturaHeladeraService = this.retrofit.create(MedidorTemperaturaHeladeraService.class);
    Call<TemperaturaDeHeladera> requetestTemperatura = medidorTemperaturaHeladeraService.temperaturaHeladera(latitud, longitud);
    Response<TemperaturaDeHeladera> temperatura = requetestTemperatura.execute();
    return temperatura.body();
  }

}
