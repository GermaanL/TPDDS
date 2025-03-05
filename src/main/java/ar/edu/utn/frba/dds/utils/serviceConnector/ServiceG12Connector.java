package ar.edu.utn.frba.dds.utils.serviceConnector;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.NoArgsConstructor;
import okhttp3.*;

import java.io.IOException;

@NoArgsConstructor
public class ServiceG12Connector {

    private static final String BASE_URL = "http://localhost:8080";
    private final OkHttpClient httpClient = new OkHttpClient();

    public void postInformacion(JsonObject jsonObject) {
        RequestBody body = RequestBody.create(jsonObject.toString(), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(BASE_URL + "/heladera/integracion")
                .post(body)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected POST code " + response);
            }
            System.out.println("Post exitoso: " + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonArray getInformacion() {
        Request request = new Request.Builder()
                .url(BASE_URL + "/heladera/uso")
                .get()
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected GET code " + response);
            }

            // Loguea la respuesta cruda
            String responseBody = response.body().string();
            System.out.println("Raw Response: " + responseBody);
            if(responseBody.equals("No data available")) {
                return new JsonArray();
            }
            // Intenta parsear
            return JsonParser.parseString(responseBody).getAsJsonArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
