package ar.edu.utn.frba.dds.utils.serviceConnector;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class RespuestaServiceDTO {
    @SerializedName("personas")
    private String nombres; // En el JSON es una cadena, pero lo convertiremos a una lista.

    @SerializedName("cantidad_personas")
    private Integer cantidadDePersonas;

    @SerializedName("localidad")
    private String localidad;

    // Método adicional para convertir nombres a lista
    public List<String> getNombresComoLista() {
        return Arrays.asList(nombres.split(", "));
    }
}
