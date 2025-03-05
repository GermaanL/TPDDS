package ar.edu.utn.frba.dds.utils.reportes;
import java.io.IOException;

public class GeneradorDeReportesMain {

    public static void main(String[] args) throws IOException {
        // Pprobar antes del cronJob:
        // en consola:
        // mvn clean package para compilar (tienen que dar bien los tests)
        // java -cp <path-(with-dependencies).jar> ar.edu.utn.frba.dds.utils.reportes.GeneradorDeReportesMain <path-archivo-salida>
        // en el task de windows, se pone "iniciar programa: java" y en "agregar argumentos (opcional): el resto de argumentos de arriba"
        // use los paths absolutos para el jar y para el txt.
        // Si es desde windows, hay que poner "iniciar en (opcional): <ruta a /2024-tpa-mi-no-grupo-07>"

        if (args.length < 1) {
            System.err.println("Uso: java -cp <path-.jar> ar.edu.utn.frba.dds.utils.reportes.GeneradorDeReportesMain <path-archivo-salida>");
            System.exit(1);
        }
        String reportePath = args[0];

        GeneradorDeReportes.realizarReporteSemanal(reportePath);
    }
}
