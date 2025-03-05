package ar.edu.utn.frba.dds.server.controllers;

import ar.edu.utn.frba.dds.domain.documentos.DocumentoDeIdentidad;
import ar.edu.utn.frba.dds.domain.documentos.TipoDoc;
import ar.edu.utn.frba.dds.domain.lugares.Direccion;
import ar.edu.utn.frba.dds.domain.personasVulnerables.PersonaVulnerable;
import ar.edu.utn.frba.dds.domain.sesion.Sesion;
import ar.edu.utn.frba.dds.repositories.PersonaVulnerableRepository;
import ar.edu.utn.frba.dds.server.framework.ICrudViewsHandler;
import io.javalin.http.Context;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class PersonaVulnerableController implements ICrudViewsHandler {
    private PersonaVulnerableRepository repositorioPersonaVulnerable;

    public PersonaVulnerableController(PersonaVulnerableRepository repositorioPersonaVulnerable) {
        this.repositorioPersonaVulnerable = repositorioPersonaVulnerable;
    }

    @Override
    public void index(Context context) {

    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {
        Map<String, Object> model = new HashMap<>();

        model.put("titulo", "Registrar Persona Vulnerable");

        String username = Sesion.obtenerUsernameDesdeSesionID(context.cookieStore().get("sesion_id"));
        if (username != null) {
            // Pasa las variables a la vista HBS
            model.put("usuarioAutenticado", true);
            model.put("nombreUsuario", username); // Nombre del usuario a mostrar
        } else {
            model.put("usuarioAutenticado", false);
        }

        context.render("/colaboraciones/registroPersonaVulnerable.hbs", model);
    }

    @Override
    public void save(Context context) {

    }

    public PersonaVulnerable savePersonaVulnerable(Context context){
        PersonaVulnerable personaVulnerable = new PersonaVulnerable();

        personaVulnerable.setNombre(context.formParam("nombre"));
        personaVulnerable.setApellido(context.formParam("apellido"));
        personaVulnerable.setFechaNacimiento(LocalDate.parse(context.formParam("fechaNacimiento")));

        DocumentoDeIdentidad documento = new DocumentoDeIdentidad();
        documento.setTipoDocumento(TipoDoc.valueOf(context.formParam("tipoDocumento")));
        documento.setNumeroDocumento(context.formParam("documento"));
        personaVulnerable.setDocumento(documento);

        personaVulnerable.setEnSituacionDeCalle(Boolean.parseBoolean(context.formParam("estaEnSituacionDeCalle")));

        //TODO faltan campos en el formulario (provincia y cp)
        Direccion direccion = new Direccion();

        direccion.setCalle(context.formParam("direccion"));
        direccion.setAltura(Integer.parseInt(context.formParam("numeroDireccion")));
        direccion.setLocalidad(context.formParam("localidad"));

        personaVulnerable.setDomicilio(direccion);
        personaVulnerable.setFechaAlta(LocalDateTime.now());
        //TODO
        //personaVulnerable.setRegistradoPor();
        personaVulnerable.setCantidadMenoresACargo(Integer.parseInt(context.formParam("cantMenoresACargo")));

        this.repositorioPersonaVulnerable.guardar(personaVulnerable);

        return personaVulnerable;
    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {

    }

    @Override
    public void delete(Context context) {

    }
}
