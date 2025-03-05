package ar.edu.utn.frba.dds.domain.services.servicioG12;

import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface AtencionMedicaService {

  @GET("heladera/uso")
  Call<List<LocalidadConPersonasVulnerables>> municiosConPersonasVulnerables();



}


