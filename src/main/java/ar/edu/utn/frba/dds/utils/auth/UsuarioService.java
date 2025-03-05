package ar.edu.utn.frba.dds.utils.auth;

import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import ar.edu.utn.frba.dds.domain.tarjetas.EstadoTarjeta;
import ar.edu.utn.frba.dds.domain.tarjetas.Tarjeta;
import ar.edu.utn.frba.dds.domain.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.repositories.ColaboradoresRepository;
import ar.edu.utn.frba.dds.repositories.TecnicosRepository;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;

import javax.swing.text.html.Option;
import java.util.Map;
import java.util.Optional;

public class UsuarioService {

    public static void agregarAtributosDeUsuario(Colaborador colaborador, String username, Map<String, Object> model) {
        if (username != null) {
            model.put("usuarioAutenticado", true);
            model.put("nombreUsuario", username);
            model.put("esAdmin", false);


            if (colaborador != null) {
                Tarjeta tarjeta = colaborador.getTarjeta();
                if (tarjeta != null) {
                    model.put("autorizarTarjeta", tarjeta.getEstado_actual() == EstadoTarjeta.Enviada);
                } else {
                    model.put("noTieneTarjeta", true);
                }

                //model.put("noTieneTarjetaAutorizada", colaborador.get().getTarjeta() == null);
                if (colaborador.esAdmin()) {
                    model.put("esAdmin", true);
                } else if (colaborador.esTecnico()) {
                    model.put("esTecnico", true); // para hacer pruebas
                } else if (colaborador.esPersona()) {
                    model.put("esPersona", true);
                } else if (colaborador.esPersonaJuridica()) {
                    model.put("esPersonaJuridica", true);
                }
            }
        } else {
            model.put("usuarioAutenticado", false);
        }
    }
    // Para tecnico
    public static void agregarAtributosDeUsuario(Tecnico tecnico, String username, Map<String, Object> model){
        if (username != null) {
            model.put("usuarioAutenticado", true);
            model.put("nombreUsuario", username);
            model.put("esAdmin", false);
            if (tecnico != null) {
                model.put("esTecnico", true);
            }
        }
        else {
            model.put("usuarioAutenticado", false);
        }

    }
}
