package ar.edu.utn.frba.dds.domain.heladeras.estados;

public class EstadoHeladeraFactory {

    public static EstadoHeladera createEstado(String unTipoDeIncidente){
        return switch (unTipoDeIncidente){
            case "ALERTA_TEMP" -> new InactivaPorTemperatura();
            case "ALERTA_MOV" -> new InactivaPorMovimiento();
            case "ALERTA_CONEXION" -> new InactivaPorConexion();
            case "FALLA_TECNICA" -> new InactivaPorFallaTecnica();
            default -> null;
        };
    }
}
