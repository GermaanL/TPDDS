package ar.edu.utn.frba.dds.server.controllers;

import ar.edu.utn.frba.dds.domain.calculadores.CalculadorDePuntos;
import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import ar.edu.utn.frba.dds.domain.contactos.Contacto;
import ar.edu.utn.frba.dds.domain.contactos.MedioDeContacto;
import ar.edu.utn.frba.dds.domain.documentos.DocumentoDeIdentidad;
import ar.edu.utn.frba.dds.domain.documentos.TipoDoc;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.lugares.AreaDeCobertura;
import ar.edu.utn.frba.dds.domain.lugares.Direccion;
import ar.edu.utn.frba.dds.domain.personasVulnerables.PersonaVulnerable;
import ar.edu.utn.frba.dds.domain.services.geocodingService.GeocodingService;
import ar.edu.utn.frba.dds.domain.sesion.Rol;
import ar.edu.utn.frba.dds.domain.sesion.Sesion;
import ar.edu.utn.frba.dds.domain.sesion.Usuario;
import ar.edu.utn.frba.dds.domain.tarjetas.Tarjeta;
import ar.edu.utn.frba.dds.domain.tarjetas.UsoDeTarjeta;
import ar.edu.utn.frba.dds.domain.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.repositories.*;
import ar.edu.utn.frba.dds.server.framework.ICrudViewsHandler;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import ar.edu.utn.frba.dds.utils.HashContrasenia;
import ar.edu.utn.frba.dds.utils.auth.UsuarioService;
import ar.edu.utn.frba.dds.utils.cargaMasiva.*;
import ar.edu.utn.frba.dds.utils.cargaMasiva.lectoresDeAchivos.LectorCSV;
import ar.edu.utn.frba.dds.utils.reportes.GeneradorDeReportes;
import ar.edu.utn.frba.dds.utils.serviceConnector.AdapterServiceG12;
import ar.edu.utn.frba.dds.utils.serviceConnector.RespuestaServiceDTO;
import ar.edu.utn.frba.dds.utils.serviceConnector.ServiceG12DTO;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class AdminController implements ICrudViewsHandler {

    private HeladerasRepository repositorioHeladeras;
    private ColaboradoresRepository colaboradoresRepository;
    private TarjetasRepository tarjetasRepository;

    public AdminController(HeladerasRepository repositorioHeladeras, ColaboradoresRepository colaboradoresRepository, TarjetasRepository tarjetasRepository) {
        this.repositorioHeladeras = repositorioHeladeras;
        this.colaboradoresRepository = colaboradoresRepository;
        this.tarjetasRepository = tarjetasRepository;
    }


    @Override
    public void index(Context context) {

        List<Heladera> heladeras = this.repositorioHeladeras.buscarTodos(Heladera.class);
        List<Colaborador> colaboradores = this.colaboradoresRepository.obtenerTodos();

        String username = Sesion.obtenerUsernameDesdeSesionID(context.cookieStore().get("sesion_id"));
        Colaborador colaborador = this.colaboradoresRepository.buscarPorUsername(username).get();
        Map<String, Object> model = new HashMap<>();

        UsuarioService.agregarAtributosDeUsuario(
                colaborador,
                username,
                model
        );

        Optional<List<Colaborador>> colaboradoresConPosiblesTarjetasAsignadas = tarjetasRepository.tarjetasAsignadas();

        model.put("titulo", "Admin");
        model.put("colaboradoresConTarjetasAsignadas", colaboradoresConPosiblesTarjetasAsignadas.orElseGet(ArrayList::new));
        //model.put("coeficientes", coeficientes);
        model.put("colaboradores", colaboradores);
        model.put("heladeras", heladeras);

        context.render("/admin/admin.hbs", model);
    }

    public void administrarTecnicos(Context context) {

        String username = Sesion.obtenerUsernameDesdeSesionID(context.cookieStore().get("sesion_id"));
        Colaborador colaborador = this.colaboradoresRepository.buscarPorUsername(username).get();
        Map<String, Object> model = new HashMap<>();
        UsuarioService.agregarAtributosDeUsuario(
                colaborador,
                username,
                model
        );

        List<Tecnico> tecnicos = ServiceLocator.instanceOf(TecnicosRepository.class).buscarTodos(Tecnico.class);
        model.put("preferenciaContacto", Arrays.stream(MedioDeContacto.values()).map(Enum::name).collect(Collectors.toList()));
        model.put("tipoDocumento", Arrays.stream(TipoDoc.values()).map(Enum::name).collect(Collectors.toList()));
        model.put("tecnicos", tecnicos);
        context.render("/admin/adminTecnicos.hbs", model);
    }

    public void cargaMasiva(Context context) {
        // Obtener el archivo subido
        UploadedFile archivoCSV = context.uploadedFile("csvFile");
        if (archivoCSV != null) {
            // Crear una instancia de CargaMasiva
            //String rutaTemporal = "uploads/" + archivoCSV.filename();
            String rutaTemporal = "/home/ubuntu/2024-tpa-mi-no-grupo-07/uploads/ofertas/" + archivoCSV.filename();

            Path rutaDestino = Paths.get(rutaTemporal);

            try {
                // Guardar el archivo en la ruta temporal
                // Eliminar el archivo si ya existe
                if (Files.exists(rutaDestino)) {
                    Files.delete(rutaDestino);
                }

                // Guardar el archivo en la ruta temporal
                Files.copy(archivoCSV.content(), rutaDestino);

                // Crear una instancia de CargaMasiva
                LectorCSV lectorCSV = new LectorCSV();
                CargaMasiva cargaMasiva = new CargaMasiva(lectorCSV);

                // Interpretar las colaboraciones leídas desde el archivo CSV
                cargaMasiva.interpretarColaboracionesLeidas(rutaTemporal);

                // Guardar las personas en el repositorio (suponiendo que ya tienes el repositorio configurado)

                System.out.print("\n-----\nCARGA MASIVA DE COLABORADORES REALIZADA CON EXITO\n-----\n");

                // Redirigir a /admin
                context.redirect("/admin");
            } catch (Exception e) {
                // Manejar excepciones si ocurre un error
                context.status(500).result("Error al procesar el archivo CSV: " + e.getMessage());
                context.redirect("/admin");
            }
        } else {
            context.status(400).result("No se recibió ningún archivo CSV.");
        }
    }

    public void formulariosIndex(Context context){
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Formularios");
        context.render("/admin/adminFormularios.hbs", model);
    }

    public void cargarTecnico(Context context) {

        Tecnico tecnico = new Tecnico();
        tecnico.setNombre(context.formParam("nombre"));
        tecnico.setApellido(context.formParam("apellido"));
        tecnico.setDocumento(new DocumentoDeIdentidad(
                TipoDoc.valueOf(context.formParam("tipoDocumento")),
                context.formParam("numeroDocumento")));
        tecnico.setCuil(context.formParam("cuil"));

        List<Contacto> contactos = new ArrayList<>();
        Contacto contacto = new Contacto();
        contacto.setTipo(MedioDeContacto.valueOf(context.formParam("preferenciaContacto")));
        contacto.setValor(context.formParam("mediosDeContacto"));
        contactos.add(contacto);
        tecnico.setMediosDeContacto(contactos);

        tecnico.setPreferenciaContacto(MedioDeContacto.valueOf(context.formParam("preferenciaContacto")));

        //Direccion
        Direccion direccion = new Direccion();
        direccion.setCalle(context.formParam("calle"));
        direccion.setAltura(Integer.parseInt(context.formParam("numeroCalle")));
        direccion.setLocalidad(context.formParam("localidad"));
        direccion.setCodigoPostal(Integer.parseInt(context.formParam("cp")));
        direccion.setProvincia(context.formParam("provincia"));

        AreaDeCobertura area = new AreaDeCobertura();
        area.setCoordenada(GeocodingService.obtenerCoordenadas(direccion));
        area.setRadioCoberturaEnKm(Double.parseDouble(context.formParam("radioCoberturaEnKm")));
        tecnico.setAreaDeCobertura(area);


        Usuario usuarioTecnico = new Usuario();
        String userNamePw = context.formParam("nombre") + (int) (Math.random() * 1000);
        usuarioTecnico.setUsername(userNamePw);
        usuarioTecnico.setPassword(HashContrasenia.generarHash(userNamePw));

        Optional<Rol> rolTecnico = ServiceLocator.instanceOf(RolRepository.class).buscarPorId(4, Rol.class);
        if(rolTecnico.isPresent()) {
            rolTecnico.get().setNombre("tecnico");
            usuarioTecnico.setRol(rolTecnico.get());
            tecnico.setUsuario(usuarioTecnico);
        }


        ServiceLocator.instanceOf(TecnicosRepository.class).guardar(tecnico);

        context.redirect("/admin");
    }

    public void verTecnico(Context context) {

        Tecnico tecnico = ServiceLocator.instanceOf(TecnicosRepository.class).
                buscarPorId(Long.parseLong(context.pathParam("id")), Tecnico.class).get();

        String username = Sesion.obtenerUsernameDesdeSesionID(context.cookieStore().get("sesion_id"));

        Map<String, Object> model = new HashMap<>();

        Optional<Colaborador> colaboradorOpt = colaboradoresRepository.buscarPorUsername(username);
        colaboradorOpt.ifPresent(colaborador -> UsuarioService.agregarAtributosDeUsuario(
                colaborador,
                username,
                model
        ));

        model.put("preferenciaContacto", Arrays.stream(MedioDeContacto.values()).map(Enum::name).collect(Collectors.toList()));
        model.put("tipoDocumento", Arrays.stream(TipoDoc.values()).map(Enum::name).collect(Collectors.toList()));
        model.put("tecnico", tecnico);
        context.render("/admin/tecnico.hbs", model);
    }

    public void modificarTecnico(Context context) {

        System.out.print("ID = " + context.formParam("id") +"\n");

        Tecnico tecnico = ServiceLocator.instanceOf(TecnicosRepository.class).
                buscarPorId(Long.parseLong(context.formParam("id")), Tecnico.class).get();
        tecnico.setNombre(context.formParam("nombre"));
        tecnico.setApellido(context.formParam("apellido"));
        tecnico.setDocumento(new DocumentoDeIdentidad(
                TipoDoc.valueOf(context.formParam("tipoDocumento")),
                context.formParam("numeroDocumento")));
        tecnico.setCuil(context.formParam("cuil"));

        /*
        List<Contacto> contactos = new ArrayList<>();
        Contacto contacto = new Contacto();
        contacto.setTipo(MedioDeContacto.valueOf(context.formParam("preferenciaContacto")));
        contacto.setValor(context.formParam("mediosDeContacto"));
        contactos.add(contacto);
        tecnico.setMediosDeContacto(contactos);

         */
        tecnico.setPreferenciaContacto(MedioDeContacto.valueOf(context.formParam("preferenciaContacto")));

        AreaDeCobertura area = new AreaDeCobertura();

        //Direccion
        String calle = context.formParam("calle");
        String numeroCalle = context.formParam("numeroCalle");
        String localidad = context.formParam("localidad");
        String cp = context.formParam("cp");
        String provincia = context.formParam("provincia");

        // Solo procede si al menos uno de los campos de dirección tiene valor
        if ((calle != null && !calle.isEmpty()) &&
            (numeroCalle != null && !numeroCalle.isEmpty()) &&
            (localidad != null && !localidad.isEmpty()) &&
            (cp != null && !cp.isEmpty()) &&
            (provincia != null && !provincia.isEmpty())) {

            Direccion direccion = new Direccion();
            direccion.setCalle(calle);
            direccion.setAltura(Integer.parseInt(numeroCalle));
            direccion.setLocalidad(localidad);
            direccion.setCodigoPostal(Integer.parseInt(cp));
            direccion.setProvincia(provincia);

            area.setCoordenada(GeocodingService.obtenerCoordenadas(direccion));
        } else {
            System.out.print( "LAT - LONG: \n" +
                    tecnico.getAreaDeCobertura().getCoordenada().getLatitud() + " " +
                    tecnico.getAreaDeCobertura().getCoordenada().getLongitud());
            area.setCoordenada(tecnico.getAreaDeCobertura().getCoordenada());
        }

        area.setRadioCoberturaEnKm(Double.parseDouble(context.formParam("radioCoberturaEnKm")));

        tecnico.setAreaDeCobertura(area);
        ServiceLocator.instanceOf(TecnicosRepository.class).actualizar(tecnico);

        context.redirect("/admin");
    }

    public void eliminarTecnico(Context context) {
        System.out.print("ID = " + context.formParam("id") +"\n");

        Tecnico tecnico = ServiceLocator.instanceOf(TecnicosRepository.class).
                buscarPorId(Long.parseLong(context.formParam("id")), Tecnico.class).get();

        ServiceLocator.instanceOf(TecnicosRepository.class).eliminar(tecnico);

        context.redirect("/admin");
    }

    public void cargarPuntos(Context context) {

    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

    }

    @Override
    public void save(Context context) {

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

    public void descargarReporte(Context context) throws FileNotFoundException {

        try {
            GeneradorDeReportes.realizarReporteSemanal("/home/ubuntu/2024-tpa-mi-no-grupo-07/uploads/reportes/reporte_semanal.txt");
        }catch(IOException e) {
            context.result("PATH INCORRECTO");
            System.out.println("ERROR AL CARGAR EL ARCHIVO: PATH INCORRECTO\n" + e);
            context.redirect("/admin");
        }

        String archivoRuta = "/home/ubuntu/2024-tpa-mi-no-grupo-07/uploads/reportes/reporte_semanal.txt";
        File archivo = new File(archivoRuta);

        if (archivo.exists()) {
            try {
                // Configura la respuesta para forzar la descarga
                context.status(200);  // HTTP 200 OK
                context.contentType("application/octet-stream"); // Tipo de archivo para descarga
                context.header("Content-Disposition", "attachment; filename=" + archivo.getName()); // Nombre de archivo para la descarga

                // Convierte el archivo a InputStream y pásalo a context.result()
                InputStream fileInputStream = new FileInputStream(archivo);
                context.result(fileInputStream);

            } catch (FileNotFoundException e) {
                context.status(500);
                context.result("Error al leer el archivo.");
            }
        } else {
            context.status(404); // Archivo no encontrado
            context.result("El archivo no existe.");
        }
    }

    public void servicioIntegrado(Context context) {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Servicio Integrado");
        AdapterServiceG12 adapter = ServiceLocator.instanceOf(AdapterServiceG12.class);

        ServiceG12DTO serviceG12DTO = new ServiceG12DTO();

        // Cargar heladeras
        List<Heladera> heladeras = repositorioHeladeras.buscarTodos(Heladera.class);
        serviceG12DTO.setHeladeras(heladeras);
        List<PersonaVulnerable> personaVulnerables = ServiceLocator.instanceOf(PersonaVulnerableRepository.class).buscarTodos(PersonaVulnerable.class);
        serviceG12DTO.setPersonasVulnerables(personaVulnerables);
        List<UsoDeTarjeta> usosDeTarjetas = tarjetasRepository.buscarTodos(UsoDeTarjeta.class);
        serviceG12DTO.setUsosDeTarjeta(usosDeTarjetas);
        try {
            adapter.postInformacion(serviceG12DTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<RespuestaServiceDTO> respuestas = new ArrayList<>();

        try {
            respuestas = adapter.getInformacion();
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.put("respuestas", respuestas);

        context.render("/admin/servicioIntegrado.hbs", model);
    }
}
