package ar.edu.utn.frba.dds.server.controllers;

import antlr.actions.cpp.ActionLexerTokenTypes;
import ar.edu.utn.frba.dds.domain.calculadores.CalculadorDeDistancias;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.incidentes.*;
import ar.edu.utn.frba.dds.domain.lugares.Ubicacion;
import ar.edu.utn.frba.dds.domain.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.repositories.ColaboradoresRepository;
import ar.edu.utn.frba.dds.repositories.HeladerasRepository;
import ar.edu.utn.frba.dds.repositories.OfertasRepository;
import ar.edu.utn.frba.dds.repositories.TecnicosRepository;
import ar.edu.utn.frba.dds.server.framework.ICrudViewsHandler;
import ar.edu.utn.frba.dds.utils.notificadores.Notificador;
import io.javalin.http.Context;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;

public class TecnicoController implements ICrudViewsHandler {

    private TecnicosRepository repositorioTecnicos;
    private HeladerasRepository repositorioHeladeras;

    public TecnicoController(TecnicosRepository repositorioTecnicos, HeladerasRepository repositorioHeladeras){
        this.repositorioTecnicos = repositorioTecnicos;
        this.repositorioHeladeras = repositorioHeladeras;
    }

    public Tecnico encontrarTecnicoCercano (Heladera heladera){

        List<Tecnico> tecnicos = repositorioTecnicos.buscarTodos(Tecnico.class);
        CalculadorDeDistancias calculador = new CalculadorDeDistancias();

        // Encontrar el técnico más cercano dentro del área de cobertura
        Optional<Tecnico> tecnicoCercano = tecnicos.stream()
                .filter(tecnico -> {
                    double distancia = calculador.calcularDistanciaEntreCoordenadas(
                            tecnico.getAreaDeCobertura().getCoordenada(),
                            heladera.getUbicadaEn().getPuntoGeografico()
                    );
                    return distancia <= tecnico.getAreaDeCobertura().getRadioCoberturaEnKm();
                })
                .min((tecnico1, tecnico2) -> {
                    double distancia1 = calculador.calcularDistanciaEntreCoordenadas(
                            tecnico1.getAreaDeCobertura().getCoordenada(),
                            heladera.getUbicadaEn().getPuntoGeografico()
                    );
                    double distancia2 = calculador.calcularDistanciaEntreCoordenadas(
                            tecnico2.getAreaDeCobertura().getCoordenada(),
                            heladera.getUbicadaEn().getPuntoGeografico()
                    );
                    return Double.compare(distancia1, distancia2);
                });

        return tecnicoCercano.orElse(null);
    }

    public void notificarTecnico(Tecnico tecnico, String mensaje){
        Notificador notificador = Notificador.of(tecnico.obtenerInfoDeContacto().getTipo());
        assert notificador != null;
        notificador.notificar(tecnico, "Se ha reportado un desperfecto en una heladera cercana a tu área de cobertura de tipo: " + mensaje);
    }

    public void reportarAlerta(Heladera heladera, TipoAlerta tipoAlerta) {
        Tecnico tecnico = encontrarTecnicoCercano(heladera);

        if(tecnico != null){
            notificarTecnico(tecnico, tipoAlerta.name());

            Alerta alerta = new Alerta();
            alerta.setFechaAlta(LocalDateTime.now());
            alerta.setHeladeraAfectada(heladera);
            alerta.setTipoIncidente(TipoIncidente.ALERTA);
            alerta.setSolucionado(false);
            alerta.setFechaSolucion(null);
            alerta.setTipoAlerta(tipoAlerta);
            tecnico.agregarIncidente(alerta);
            repositorioTecnicos.actualizar(tecnico);
        }
    }

    public void reportarFalla(Heladera heladera, FallaTecnica fallaTecnica){
        Tecnico tecnico = encontrarTecnicoCercano(heladera);

        if(tecnico != null){
            notificarTecnico(tecnico, "FALLA_TECNICA");
            tecnico.agregarIncidente(fallaTecnica);
            repositorioTecnicos.actualizar(tecnico);
        }
    }

    @Override
    public void index(Context context) {

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
}
