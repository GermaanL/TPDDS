package ar.edu.utn.frba.dds.domain.services.servicioG12;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;
import java.util.List;

public class AtencionMedicaImplementation {
  private static AtencionMedicaImplementation instance = null;
  //private static final String urlBaseApi = "https://bed97a75-bbef-4250-87f6-3e88aa2e7f53.mock.pstmn.io";
  private static final String urlBaseApi = "http://localhost:8080";

  private Retrofit retrofit;

  private AtencionMedicaImplementation(){
    this.retrofit = new Retrofit.Builder().baseUrl(urlBaseApi).addConverterFactory(GsonConverterFactory.create()).build();
  }
  public static AtencionMedicaImplementation getInstance(){
    if (instance == null){
      instance = new AtencionMedicaImplementation();
    }
    return instance;
  }

  public List<LocalidadConPersonasVulnerables> obtenerListadoMunicipios() throws IOException {
    AtencionMedicaService atencionMedicaService = this.retrofit.create(AtencionMedicaService.class);
    Call<List<LocalidadConPersonasVulnerables>> requetestMunicipios = atencionMedicaService.municiosConPersonasVulnerables();
    try {
      Response<List<LocalidadConPersonasVulnerables>> responseListadoDePuntos = requetestMunicipios.execute();
      return responseListadoDePuntos.body();
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
