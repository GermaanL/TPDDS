package ar.edu.utn.frba.dds.utils.sensores;
/*

import ar.edu.utn.frba.dds.utils.LocalDateTimeAdapterJson;
import com.google.gson.*;

import java.time.LocalDateTime;
import java.util.Random;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class GeneradorDeTemperatura {

    private static final double TEMPERATURA_MINIMA = 0.00;
    private static final double TEMPERATURA_MAXIMA = 40.00;
    private static final Random RANDOM = new Random();

    // Método para generar un valor de temperatura aleatoria con dos decimales
    private static double generarTemperaturaAleatoria() {
        double temperatura = TEMPERATURA_MINIMA + (TEMPERATURA_MAXIMA - TEMPERATURA_MINIMA) * RANDOM.nextDouble();
        return redondear(temperatura, 1);
    }

    // Método para redondear un valor a un número especificado de decimales
    private static double redondear(double valor, int decimales) {
        BigDecimal bd = new BigDecimal(Double.toString(valor));
        bd = bd.setScale(decimales, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    // Método para generar el JSON con temperatura aleatoria
    public static String generarJson(String idHeladera) {
        double temperatura = generarTemperaturaAleatoria();
        LocalDateTime horaActual = LocalDateTime.now();

        // Crear instancia de MedicionTemperatura
        MedicionTemperatura medicion = new MedicionTemperatura(temperatura, horaActual);

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
*/