package ar.edu.utn.frba.dds.utils.sensores;

import ar.edu.utn.frba.dds.domain.heladeras.sensores.movimiento.MovimientoDetectado;
import ar.edu.utn.frba.dds.utils.LocalDateTimeAdapterJson;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;

public class GeneradorDeMovimiento {

    public static String generarJson(String idHeladera) {
        LocalDateTime horaActual = LocalDateTime.now();

        // Crear instancia de MedicionTemperatura
        MovimientoDetectado medicion = new MovimientoDetectado(idHeladera, horaActual);

        // Configurar Gson para formatear correctamente el JSON
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapterJson())
                .setPrettyPrinting()
                .create();

        // Convertir objeto MedicionTemperatura a JSON
        String jsonString = gson.toJson(medicion);

        return jsonString;
    }
}
