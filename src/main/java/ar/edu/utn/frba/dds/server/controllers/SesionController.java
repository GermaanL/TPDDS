package ar.edu.utn.frba.dds.server.controllers;

import ar.edu.utn.frba.dds.domain.calculadores.CalculadorDePuntos;
import ar.edu.utn.frba.dds.domain.colaboraciones.FormaDeColaboracion;
import ar.edu.utn.frba.dds.domain.colaboradores.*;
import ar.edu.utn.frba.dds.domain.sesion.Accion;
import ar.edu.utn.frba.dds.repositories.*;
import ar.edu.utn.frba.dds.server.controllers.capaServicios.RegistroServiceLayer;
import ar.edu.utn.frba.dds.domain.contactos.Contacto;
import ar.edu.utn.frba.dds.domain.contactos.MedioDeContacto;
import ar.edu.utn.frba.dds.domain.documentos.DocumentoDeIdentidad;
import ar.edu.utn.frba.dds.domain.documentos.TipoDoc;
import ar.edu.utn.frba.dds.domain.lugares.Direccion;
import ar.edu.utn.frba.dds.domain.sesion.Rol;
import ar.edu.utn.frba.dds.domain.sesion.Sesion;
import ar.edu.utn.frba.dds.domain.sesion.Usuario;
import ar.edu.utn.frba.dds.server.framework.ICrudViewsHandler;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import ar.edu.utn.frba.dds.utils.HashContrasenia;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class SesionController implements ICrudViewsHandler {

    SesionRepository sesionRepository;

    public SesionController(SesionRepository sesionRepository){
        this.sesionRepository = sesionRepository;
    }

    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();

        model.put("titulo", "Iniciar Sesión");

        context.render("/registros/login.hbs", model);
    }

    public void loginPost(Context context) {
        String username = context.formParam("username");
        String contrasenia = context.formParam("contrasenia");

        Optional<Usuario> usuario = sesionRepository.buscarPorId(username, Usuario.class);

        if (usuario.isPresent() && HashContrasenia.verificarContrasenia(contrasenia, usuario.get().getPassword())) {
            Sesion.iniciarSesion(context);

            //CALCULO DE PUNTOS
            Colaborador colaborador = ServiceLocator.instanceOf(ColaboradoresRepository.class)
                    .buscarPorUsername(username)
                    .orElse(null);
            Optional<CalculadorDePuntos> calculadorOpt = ServiceLocator.instanceOf(CalculadorDePuntosRepository.class).buscarPorId(1L, CalculadorDePuntos.class);
            if(calculadorOpt.isPresent() && colaborador != null) {
                colaborador.calcularPuntos(calculadorOpt.get());
                ServiceLocator.instanceOf(ColaboradoresRepository.class).actualizar(colaborador);
            }

            context.redirect("/main");
        }
        else {
            context.redirect("/login");
        }
    }

    @Override
    public void show(Context context) {}

    @Override
    public void create(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<FormaDeColaboracion> formasColabs = ServiceLocator.instanceOf(FormaDeColaboracionRepository.class).buscarTodos(FormaDeColaboracion.class)
                .stream().filter(f->TipoPersona.HUMANA.equals(f.getRealizablePor()) || TipoPersona.TODOS.equals(f.getRealizablePor())).
                toList();
        model.put("contribuciones",formasColabs);

        model.put("titulo", "Registro");

        context.render("/registros/registro.hbs", model);
    }

    public void logout(Context context) {
        context.cookieStore().clear();
        context.redirect("/main");
    }
    
    @Override
    public void save(Context context) {

    }

    public Context saveRegistro(Context context) {

        if (RegistroServiceLayer.validarUsuario(context)) {
            return context.status(400).json(Map.of("respuesta", "El usuario ya existe"));
        }
        if (!RegistroServiceLayer.validarContrasenia(context)) {
            return context.status(400).json(Map.of("respuesta", "La contraseña no cumple con los requisitos"));
        }


        Usuario usuario = new Usuario();
        usuario.setUsername(context.formParam("username"));
        usuario.setPassword(HashContrasenia.generarHash(context.formParam("password")));

        PersonaHumana personaHumana = new PersonaHumana();

        personaHumana.setNombre(context.formParam("nombre"));
        personaHumana.setApellido(context.formParam("apellido"));
        personaHumana.setFechaNacimiento(LocalDate.parse(context.formParam("fechaNacimiento")));

        DocumentoDeIdentidad documento = new DocumentoDeIdentidad();
        documento.setTipoDocumento(TipoDoc.valueOf(context.formParam("tipoDocumento")));
        documento.setNumeroDocumento(context.formParam("documento"));
        personaHumana.setDocumento(documento);

        Direccion direccion = new Direccion();
        direccion.setCalle(context.formParam("calle"));
        direccion.setAltura(Integer.parseInt(context.formParam("numero")));
        direccion.setLocalidad(context.formParam("localidad"));

        personaHumana.setDireccion(direccion);

        //TODO añadir roles
        ServiceLocator.instanceOf(RolRepository.class).buscarPorId(2L, Rol.class).ifPresent(usuario::setRol);

        personaHumana.setUsuario(usuario);

        personaHumana.setPreferenciaContacto(MedioDeContacto.valueOf("MAIL"));
        Contacto contacto = new Contacto();
        contacto.setTipo(MedioDeContacto.valueOf("MAIL"));
        contacto.setValor(context.formParam("email"));

        List<Contacto> contactos = new ArrayList<Contacto>();

        contactos.add(contacto);
        personaHumana.setMediosDeContacto(contactos);

        personaHumana.setPuntosDisponibles(0.0);
        personaHumana.setPuntosTotales(0.0);
        personaHumana.setPuntosCanjeados(0.0);

        List<String> formasSeleccionadasStr = context.formParams("formasSeleccionadas");
        System.out.println("Formas seleccionadas: " + formasSeleccionadasStr);
        List<FormaDeColaboracion> formasSeleccionadas = convertirNombresAFormasDeColaboracion(formasSeleccionadasStr);
        personaHumana.setFormasDeColaborar(formasSeleccionadas);

        List<Accion> accionesUsuario = mapearFormasAAcciones(formasSeleccionadasStr);
        usuario.setAcciones(accionesUsuario);

        personaHumana.setColaboracionesRealizadas(null);

        //sesionRepository.guardar(usuario); El de abajo tiene Cascade->PERSIST

        // COMENTADO PARA HACER PRUEBAS - DESCOMENTAR LINEA DE ABAJO
        ServiceLocator.instanceOf(ColaboradoresRepository.class).guardar(personaHumana);

        return context.status(201);

    }

    public void createPersonaJuridica(Context context) {
        Map<String, Object> model = new HashMap<>();

        List<FormaDeColaboracion> formasColabs = ServiceLocator.instanceOf(FormaDeColaboracionRepository.class).buscarTodos(FormaDeColaboracion.class)
                .stream().filter(f->TipoPersona.JURIDICA.equals(f.getRealizablePor()) || TipoPersona.TODOS.equals(f.getRealizablePor())).
                toList();
        model.put("contribuciones",formasColabs);

        model.put("titulo", "Registro Persona Juridica");

        model.put("tipos", Arrays.stream(TipoJuridico.values()).map(Enum::name).collect(Collectors.toList()));

        context.render("/registros/registroPersonaJuridica.hbs", model);
    }

    public Context savePersonaJuridica(Context context) {

        if (RegistroServiceLayer.validarUsuario(context)) {
            return context.status(400).json(Map.of("respuesta", "El usuario ya existe"));
        }
        if (!RegistroServiceLayer.validarContrasenia(context)) {
            return context.status(400).json(Map.of("respuesta", "La contraseña no cumple con los requisitos"));
        }

        PersonaJuridica personaJuridica = new PersonaJuridica();

        personaJuridica.setRazonSocial(context.formParam("razonsocial"));
        personaJuridica.setRubro(context.formParam("rubro"));
        personaJuridica.setTipoJuridico(TipoJuridico.valueOf(context.formParam("tipoJuridico")));

        Direccion direccion = new Direccion();
        direccion.setCalle(context.formParam("calle"));
        direccion.setAltura(Integer.parseInt(context.formParam("numero")));
        direccion.setLocalidad(context.formParam("localidad"));

        personaJuridica.setDireccion(direccion);

        List<String> formasSeleccionadasStr = context.formParams("formasSeleccionadas");
        personaJuridica.setFormasDeColaborar(convertirNombresAFormasDeColaboracion(formasSeleccionadasStr));

        Usuario usuario = new Usuario();
        usuario.setUsername(context.formParam("username"));
        usuario.setPassword(HashContrasenia.generarHash(context.formParam("password")));
        usuario.setAcciones(mapearFormasAAcciones(formasSeleccionadasStr));
        personaJuridica.setUsuario(usuario);

        //TODO añadir roles
        ServiceLocator.instanceOf(RolRepository.class).buscarPorId(3L, Rol.class).ifPresent(usuario::setRol);

        personaJuridica.setPreferenciaContacto(MedioDeContacto.valueOf("MAIL"));
        Contacto contacto = new Contacto();
        contacto.setTipo(MedioDeContacto.valueOf("MAIL"));
        contacto.setValor(context.formParam("email"));

        List<Contacto> contactos = new ArrayList<Contacto>();
        contactos.add(contacto);

        personaJuridica.setMediosDeContacto(contactos);

        personaJuridica.setPuntosDisponibles(0.0);
        personaJuridica.setPuntosTotales(0.0);
        personaJuridica.setPuntosCanjeados(0.0);


        personaJuridica.setColaboracionesRealizadas(null);

        //sesionRepository.guardar(usuario);
        ServiceLocator.instanceOf(ColaboradoresRepository.class).guardar(personaJuridica);

        context.redirect("/login");
        return context.status(201);
    }

    @Override
    public void edit(Context context) {
        this.sesionRepository.buscarPorId(Long.parseLong(context.pathParam("id")), Usuario.class).ifPresent(usuario -> {
            usuario.setUsername(context.formParam("nombre"));
            this.sesionRepository.actualizar(usuario);
        });
    }

    @Override
    public void update(Context context) {
        //TODO
    }

    @Override
    public void delete(Context context) {
        String id = context.pathParam("id");
        this.sesionRepository.buscarPorId(Long.parseLong(id), Usuario.class).ifPresent(usuario -> {
            ServiceLocator.instanceOf(ColaboradoresRepository.class)
                    .eliminar(ServiceLocator.instanceOf(ColaboradoresRepository.class)
                    .buscarPorUsername(usuario.getUsername()).get());
        });
    }

    public void sinPermiso(Context context) {
        Map<String, Object> model = new HashMap<>();

        context.render("/sinPermiso.hbs", model);
    }


    private List<Accion> mapearFormasAAcciones(List<String> nombresFormas) {
        AccionesRepository accionesRepository = ServiceLocator.instanceOf(AccionesRepository.class);

        return nombresFormas.stream()
                .flatMap(nombreForma -> {
                    // Mapear cada nombre de forma a su acción correspondiente
                    Optional<Accion> accion = switch (nombreForma) {
                        case "Donacion de Dinero" -> accionesRepository.buscarPorNombre("/colaborar/donacionDeDinero");
                        case "Donacion de Vianda" -> accionesRepository.buscarPorNombre("/colaborar/donacionDeVianda");
                        case "Distribucion de Vianda" -> accionesRepository.buscarPorNombre("/colaborar/distribucionDeVianda");
                        case "Registro Persona Vulnerable" -> accionesRepository.buscarPorNombre("/colaborar/registroPersonaVulnerable");
                        case "Colocacion de Heladera" -> accionesRepository.buscarPorNombre("/colaborarPersonaJuridica/heladera");
                        case "Ofertar" -> accionesRepository.buscarPorNombre("/colaborarPersonaJuridica/ofertaProducto");
                        default -> Optional.empty();
                    };
                    return accion.stream(); // Convertir el Optional en un Stream para filtrar vacíos automáticamente
                })
                .toList(); // Recolectar las acciones en una lista
    }

    // Método para convertir los nombres seleccionados en una lista de objetos FormaDeColaboracion
    private List<FormaDeColaboracion> convertirNombresAFormasDeColaboracion(List<String> nombresSeleccionados) {
        List<FormaDeColaboracion> formasSeleccionadas = new ArrayList<>();

        // Recorrer los nombres seleccionados y buscar las formas correspondientes en el repositorio
        for (String nombre : nombresSeleccionados) {
            Optional<FormaDeColaboracion> forma = ServiceLocator.
                    instanceOf(FormaDeColaboracionRepository.class).
                    buscarPorNombre(nombre);
            forma.ifPresent(formasSeleccionadas::add);
        }

        return formasSeleccionadas; // Devolver la lista de formas seleccionadas
    }
}
