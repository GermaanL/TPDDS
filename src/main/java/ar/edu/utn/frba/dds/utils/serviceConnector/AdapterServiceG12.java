package ar.edu.utn.frba.dds.utils.serviceConnector;

import ar.edu.utn.frba.dds.domain.personasVulnerables.PersonaVulnerable;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
public class AdapterServiceG12 implements IAdapterServiceG12{

    private ServiceG12Connector serviceG12Connector;

    /*
    De las heladeras necesito mandarle el id: heladera.getId()
                                       el lugar_id: heladera.getUbicadaEn().getId()
    De las ubicaciones necesito mandarle la localidad: heladera.getUbicadaEn().getDireccion().getLocalidad()
                                                       heladera.getUbicadaEn().getId()
    De las personas vulnerables necesito mandarle el nombre: personaVulnerable.getNombre()
                                                             personaVulnerable.getApellido()
                                                             personaVulnerable.getFechaNacimiento().until(LocalDate.now(), ChronoUnit.YEARS)
    De los usos de tarjeta necesito mandarle el id: usoDeTarjeta.getId()
                                            la fechaHora: usoDeTarjeta.getFechaHora()
                                            el id de la tarjeta usada: usoDeTarjeta.getTarjetaUsada().getId()
                                            el id de la heladera usada: usoDeTarjeta.getHeladeraUsada().getId()

    Json ejemplo:
    {
    heladeras: [
        {
            "id": 1,
            "lugar_id": 1
        },
        {
            "id": 2,
            "lugar_id": 2
        }
    ],
    ubicaciones: [
        {
            "localidad": "CABA",
            "id": 1
        },
        {
            "localidad": "Tres de Febrero",
            "id": 2
        }
    ],
    personasVulnerables: [
        {
            "nombre": "Juan",
            "apellido": "Perez",
            "edad": 30
        },
        {
            "nombre": "Maria",
            "apellido": "Gomez",
            "edad": 40
        }
    ],
    usosDeTarjeta: [
        {
            "id": 1,
            "fechaHora": "2021-06-01T10:15:30",
            "tarjetaUsada_id": 1,
            "heladeraUsada_id": 1
        },
        {
            "id": 2,
            "fechaHora": "2021-06-01T10:15:30",
            "tarjetaUsada_id": 2,
            "heladeraUsada_id": 2
        }
    ]
    }
    * */


    public void postInformacion(ServiceG12DTO serviceG12DTO) {
        JsonObject jsonObject = new JsonObject();

        // Convertir heladeras a JSON
        JsonArray heladerasArray = new JsonArray();
        serviceG12DTO.getHeladeras().forEach(heladera -> {
            JsonObject heladeraJson = new JsonObject();
            heladeraJson.addProperty("id", heladera.getId());
            heladeraJson.addProperty("ubicacion_id", heladera.getUbicadaEn().getId());
            heladerasArray.add(heladeraJson);
        });
        jsonObject.add("heladeras", heladerasArray);

        // Convertir ubicaciones a JSON
        JsonArray ubicacionesArray = new JsonArray();
        serviceG12DTO.getHeladeras().forEach(heladera -> {
            JsonObject ubicacionJson = new JsonObject();
            ubicacionJson.addProperty("localidad", heladera.getUbicadaEn().getDireccion().getLocalidad());
            ubicacionJson.addProperty("ubicacion_id", heladera.getUbicadaEn().getId());
            ubicacionesArray.add(ubicacionJson);
        });
        jsonObject.add("ubicaciones", ubicacionesArray);

        // Convertir personas vulnerables a JSON
        JsonArray personasArray = new JsonArray();
        serviceG12DTO.getPersonasVulnerables().forEach(persona -> {
            JsonObject personaJson = new JsonObject();
            personaJson.addProperty("id", persona.getId());
            personaJson.addProperty("nombre", persona.getNombre());
            personaJson.addProperty("apellido", persona.getApellido());
            personasArray.add(personaJson);
        });
        jsonObject.add("personas", personasArray);

        // Convertir usos de tarjeta a JSON
        JsonArray usosArray = new JsonArray();
        serviceG12DTO.getUsosDeTarjeta().forEach(uso -> {
            JsonObject usoJson = new JsonObject();
            usoJson.addProperty("id", uso.getId());
            usoJson.addProperty("fechaHora", uso.getFechaHora().toString());
            usoJson.addProperty("tarjeta_id", uso.getTarjetaUsada().getId());
            usoJson.addProperty("heladera_id", uso.getHeladeraUsada().getId());
            usoJson.addProperty("personaVulnerable_id", this.personaPorUso(uso.getTarjetaUsada().getId(), serviceG12DTO.getPersonasVulnerables()));
            usosArray.add(usoJson);
        });
        jsonObject.add("usosDeTarjeta", usosArray);


        System.out.println(jsonObject.toString());

        // Enviar JSON al servicio
        serviceG12Connector.postInformacion(jsonObject);
    }

    private String personaPorUso(Long idTarjeta, List<PersonaVulnerable> personasVulnerables) {
        // Buscar la persona vulnerable que tiene la tarjeta con el id pasado por parámetro
        for (PersonaVulnerable persona : personasVulnerables) {
            if (persona.getTarjeta().getId().equals(idTarjeta)) {
                return persona.getId().toString();
            }
        }

        return null;
    }

    @Override
    public List<RespuestaServiceDTO> getInformacion() {
        JsonArray jsonArray = serviceG12Connector.getInformacion();
        if (jsonArray == null || !jsonArray.isJsonArray()) {
            throw new IllegalArgumentException("La respuesta del servicio no es válida o no es un array.");
        }

        System.out.println("Contenido del JsonArray: " + jsonArray);

        // Usa Gson para deserializar el array
        Gson gson = new Gson();

        // Define el tipo de la lista
        Type listType = new TypeToken<List<RespuestaServiceDTO>>() {}.getType();

        //System.out.print(Optional.ofNullable(gson.fromJson(jsonArray, listType)));
        // Parsear el array al tipo requerido
        return gson.fromJson(jsonArray, listType);
    }
}
