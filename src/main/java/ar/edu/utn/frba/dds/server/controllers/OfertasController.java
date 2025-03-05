package ar.edu.utn.frba.dds.server.controllers;

import ar.edu.utn.frba.dds.domain.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaJuridica.ofertas.Oferta;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaJuridica.ofertas.Ofertar;
import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import ar.edu.utn.frba.dds.domain.sesion.Sesion;
import ar.edu.utn.frba.dds.domain.sesion.Usuario;
import ar.edu.utn.frba.dds.repositories.ColaboradoresRepository;
import ar.edu.utn.frba.dds.repositories.OfertasRepository;
import ar.edu.utn.frba.dds.repositories.SesionRepository;
import ar.edu.utn.frba.dds.server.framework.ICrudViewsHandler;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import ar.edu.utn.frba.dds.utils.auth.UsuarioService;
import io.javalin.http.Context;

import java.security.Provider;
import java.util.*;
import java.util.stream.Collectors;

public class OfertasController implements ICrudViewsHandler {

    private OfertasRepository repositorioOfertas;
    private ColaboradoresRepository repositorioColaboradores;

    public OfertasController(OfertasRepository repositorioOfertas, ColaboradoresRepository repositorioColaboradores){
        this.repositorioColaboradores = repositorioColaboradores;
        this.repositorioOfertas = repositorioOfertas;
    }

    @Override
    public void index(Context context){
        List<Oferta> ofertas = this.repositorioOfertas.buscarTodos(Oferta.class);
        //Chequea mostrar solo ofertas con stock.

        // Filtrar solo las ofertas que tienen stock, tratando null como false
        ofertas = ofertas.stream()
                .filter(oferta -> oferta.getStock() > 0) // Interpreta null como false
                .toList();

        String username = Sesion.obtenerUsernameDesdeSesionID(context.cookieStore().get("sesion_id"));
        Optional<Colaborador> colaborador = this.repositorioColaboradores.buscarPorUsername(username);
        Map<String, Object> model = new HashMap<>();

        UsuarioService.agregarAtributosDeUsuario(colaborador.get(), username, model);

        model.put("puntosColaborador", colaborador.get().getPuntosDisponibles());
        model.put("titulo", "Ofertas");

        // Procesar los detalles de la oferta y añadirlos al modelo
        model.put("ofertas", ofertas);

        context.render("/ofertas/ofertas.hbs", model);
    }

    public void ofertaProductoCanjear(Context context) {
        //System.out.println("Canjeando oferta con id: " + context.pathParam("id"));

        Optional<Oferta> oferta = repositorioOfertas.buscarPorId(Long.parseLong(context.pathParam("id")), Oferta.class);

        if(oferta.isPresent() && (oferta.get().getStock() > 0 || !oferta.get().getSinStock())) {

            String username = ServiceLocator.instanceOf(SesionRepository.class).buscarPorId(context.cookieStore().get("sesion_id").toString(), Sesion.class).get().getUsuario().getUsername();

            Optional<Colaborador> colaborador = repositorioColaboradores.buscarPorUsername(username);

            try {
                if(colaborador.isPresent()) {
                    colaborador.get().canjearPuntos(oferta.get());
                    oferta.get().serCanjeado();

                    //TODO notificar al colaborador que canjeo la oferta

                    repositorioOfertas.actualizar(oferta.get());
                    repositorioColaboradores.actualizar(colaborador.get());

                    context.redirect("/ofertas");
                }
            }
            catch (Exception e) {
                //System.out.println("Error al canjear oferta:--------------------------------------- " + e.getMessage());
                context.redirect("/ofertas");
                return;
            }

        } else {
            //TODO devolver otro error
            //System.out.println("NO TENES STOCK ---------------------------------------");
            context.redirect("/main");
        }
    }

    public void misOfertasIndex(Context context){
        Map<String, Object> model = new HashMap<>();

        String username = Sesion.obtenerUsernameDesdeSesionID(context.cookieStore().get("sesion_id"));
        Optional<Colaborador> colaborador = repositorioColaboradores.buscarPorUsername(username);

        UsuarioService.agregarAtributosDeUsuario(colaborador.get(), username, model);

        List<Colaboracion> colaboracionesRealizadas = colaborador.get().getColaboracionesRealizadas();

        // Filtra solo las instancias de Ofertar
        List<Ofertar> ofertas = colaboracionesRealizadas.stream()
                .filter(colaboracion -> colaboracion instanceof Ofertar)
                .map(colaboracion -> (Ofertar) colaboracion)
                .collect(Collectors.toList());

        if (username != null) {
            // Pasa las variables a la vista HBS
            model.put("usuarioAutenticado", true);
            model.put("nombreUsuario", username); // Nombre del usuario a mostrar
        } else {
            model.put("usuarioAutenticado", false);
        }

        model.put("titulo", "Mis Ofertas");

        // Procesar los detalles de la oferta y añadirlos al modelo
        model.put("ofertas", ofertas);
        model.put("puntosColaborador", colaborador.get().getPuntosDisponibles());

        context.render("/ofertas/misOfertas.hbs", model);
    }

    public void eliminarOferta(Context context) {
        // Elimino el ofertar y el cascade elimina la oferta
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


    public void editarOferta(Context context) {
        Optional<Oferta> oferta = repositorioOfertas.buscarPorId(Long.parseLong(context.pathParam("id")), Oferta.class);
        if(oferta.isPresent()){
            oferta.get().setNombre((Objects.requireNonNull(context.formParam("nombre"))));
            oferta.get().setRubro((Objects.requireNonNull(context.formParam("rubro"))));
            oferta.get().setPuntosNecesarios(Integer.parseInt(Objects.requireNonNull(context.formParam("puntosNecesarios"))));
            oferta.get().setStock(Integer.parseInt(Objects.requireNonNull(context.formParam("stock"))));
            repositorioOfertas.actualizar(oferta.get());
        }
        context.redirect("/misOfertas");
    }

    public void agregarStock(Context context) {
        Optional<Oferta> oferta = repositorioOfertas.buscarPorId(Long.parseLong(context.pathParam("id")), Oferta.class);
        if(oferta.isPresent()){
            oferta.get().setStock(Integer.parseInt(Objects.requireNonNull(context.formParam("stock"))));
            oferta.get().setSinStock(false);
            repositorioOfertas.actualizar(oferta.get());
        }
        context.redirect("/misOfertas");
    }
}
