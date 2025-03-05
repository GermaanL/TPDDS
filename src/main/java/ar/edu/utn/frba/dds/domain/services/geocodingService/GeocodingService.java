package ar.edu.utn.frba.dds.domain.services.geocodingService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;

import ar.edu.utn.frba.dds.domain.lugares.Coordenada;
import ar.edu.utn.frba.dds.domain.lugares.Direccion;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GeocodingService {

    private static final String NOMINATIM_URL = "https://nominatim.openstreetmap.org/search?format=json&q=";

    public static Coordenada obtenerCoordenadas(Direccion dir) {

        String calle = dir.getCalle();
        Integer altura = dir.getAltura();
        Integer codigoPostal = dir.getCodigoPostal();
        String localidad = dir.getLocalidad();
        String provincia = dir.getProvincia();
        try {
            // Construye la dirección en una sola cadena
            String direccion = String.join(" ", calle, String.valueOf(altura), String.valueOf(codigoPostal), localidad, provincia);
            String urlStr = NOMINATIM_URL + direccion.replace(" ", "+");
            URL url = new URL(urlStr);

            // Configura la conexión HTTP
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            // Lee la respuesta
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parsea la respuesta JSON
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response.toString());
            if (node.isArray() && node.size() > 0) {
                JsonNode location = node.get(0);
                Double latitud = location.get("lat").asDouble();
                Double longitud = location.get("lon").asDouble();
                return new Coordenada(latitud, longitud); // Devuelve una instancia de Coordenada
            } else {
                throw new RuntimeException("No se encontraron coordenadas para la dirección proporcionada.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        GeocodingService geocodingService = new GeocodingService();
        Direccion direccion = new Direccion("Manzoni", 102, 1407, "Buenos Aires","Villa Luro" );
        Coordenada coordenada = geocodingService.obtenerCoordenadas(direccion);
        System.out.print(coordenada.getLatitud() + " " + coordenada.getLongitud());
    }
}
