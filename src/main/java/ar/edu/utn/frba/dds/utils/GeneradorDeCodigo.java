package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.repositories.TarjetasRepository;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

public class GeneradorDeCodigo {

    private static final String CARACTERES_PERMITIDOS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LONGITUD_CODIGO = 16;

    public static String generarCodigo() {
        SecureRandom random = new SecureRandom();
        String codigo;

        StringBuilder sb = new StringBuilder(LONGITUD_CODIGO);
        for (int i = 0; i < LONGITUD_CODIGO; i++) {
            int indice = random.nextInt(CARACTERES_PERMITIDOS.length());
            sb.append(CARACTERES_PERMITIDOS.charAt(indice));
        }
        codigo = sb.toString();

        while (ServiceLocator.instanceOf(TarjetasRepository.class).buscarPorCodigo(codigo).isPresent()) {
            sb = new StringBuilder(LONGITUD_CODIGO);
            for (int i = 0; i < LONGITUD_CODIGO; i++) {
                int indice = random.nextInt(CARACTERES_PERMITIDOS.length());
                sb.append(CARACTERES_PERMITIDOS.charAt(indice));
            }
            codigo = sb.toString();
        }

        return codigo;
    }

    public static void main(String[] args) {
        System.out.println(generarCodigo());
    }

}

