package ar.edu.utn.frba.dds.utils.reportes;

import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaHumana.DistribucionDeVianda;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaHumana.DonacionDeVianda;
import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.incidentes.FallaTecnica;
import ar.edu.utn.frba.dds.domain.incidentes.Incidente;
import ar.edu.utn.frba.dds.repositories.ColaboracionRepository.ColaboracionesRepository;
import ar.edu.utn.frba.dds.repositories.ColaboradoresRepository;
import ar.edu.utn.frba.dds.repositories.HeladerasRepository;
import ar.edu.utn.frba.dds.repositories.IncidentesRepository;
import ar.edu.utn.frba.dds.repositories.ReporteFallaRepository;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import ar.edu.utn.frba.dds.utils.FileWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GeneradorDeReportes {

    public static void realizarReporteSemanal(String reporteSemanalPath) throws IOException {
        LocalDateTime unaSemanaAtras = LocalDateTime.now().minusWeeks(1);

        Map<Integer, Long> fallasPorHeladera = obtenerFallasPorHeladera(unaSemanaAtras);
        Map<Integer, Integer> viandasRetiradasPorHeladera = obtenerDistribucionesVianda(unaSemanaAtras, true);
        Map<Integer, Integer> viandasColocadasPorHeladera = obtenerDistribucionesVianda(unaSemanaAtras, false);
        Map<Integer, Integer> viandasPorColaborador = obtenerDonacionesVianda(unaSemanaAtras);

        FileWriter.write("\n", reporteSemanalPath);
        FileWriter.write("Reporte Semanal [" + LocalDate.now() + "]\n\n", reporteSemanalPath);

        FileWriter.write("Cantidad de Fallas por Heladera:\n", reporteSemanalPath);
        fallasPorHeladera.forEach((heladeraId, count) -> {
            FileWriter.write("Heladera \"" +
                    ServiceLocator.instanceOf(HeladerasRepository.class).buscarPorId(Long.valueOf(heladeraId), Heladera.class).get().getNombre() +
                    "\": " + count + "\n", reporteSemanalPath);
        });
        FileWriter.write("\n", reporteSemanalPath);

        FileWriter.write("Cantidad de Viandas Retiradas por Heladera:\n", reporteSemanalPath);
        viandasRetiradasPorHeladera.forEach((heladeraId, cantidad) -> {
            FileWriter.write("Heladera \"" +
                    ServiceLocator.instanceOf(HeladerasRepository.class).buscarPorId(Long.valueOf(heladeraId), Heladera.class).get().getNombre() +
                    "\": " + cantidad + "\n", reporteSemanalPath);
        });
        FileWriter.write("\n", reporteSemanalPath);

        FileWriter.write("Cantidad de Viandas Colocadas por Heladera:\n", reporteSemanalPath);
        viandasColocadasPorHeladera.forEach((heladeraId, cantidad) -> {
            FileWriter.write("Heladera \"" +
                    ServiceLocator.instanceOf(HeladerasRepository.class).buscarPorId(Long.valueOf(heladeraId), Heladera.class).get().getNombre() +
                    "\": " + cantidad + "\n", reporteSemanalPath);
        });
        FileWriter.write("\n", reporteSemanalPath);

        FileWriter.write("Cantidad de Viandas por Colaborador:\n", reporteSemanalPath);
        viandasPorColaborador.forEach((colaboradorId, cantidad) -> {
            FileWriter.write("Colaborador \"" +
                    ServiceLocator.instanceOf(ColaboradoresRepository.class).buscarPorId(Long.valueOf(colaboradorId), Colaborador.class).get().getUsuario().getUsername() +
                    "\": " + cantidad + "\n", reporteSemanalPath);
        });
    }

    private static Map<Integer, Long> obtenerFallasPorHeladera(LocalDateTime fechaInicio) {
        // Método que obtendría las fallas agrupadas por ID de heladera desde el repositorio
        // Ejemplo de código que puedes adaptar según tus necesidades:
        List<Incidente> fallas = ServiceLocator.instanceOf(IncidentesRepository.class).
                buscarTodos(Incidente.class)
                .stream()
                .filter(d -> d.getFechaAlta().isAfter(fechaInicio))
                .toList();
        Map<Integer, Long> fallasPorHeladera = new HashMap<>();
        fallas.forEach(falla -> fallasPorHeladera.merge(Math.toIntExact(falla.getHeladeraAfectada().getId()), 1L, Long::sum));
        return fallasPorHeladera;
    }

    private static Map<Integer, Integer> obtenerDistribucionesVianda(LocalDateTime fechaInicio, boolean retiradas) {
        List<DistribucionDeVianda> distribuciones = ServiceLocator.instanceOf(ColaboracionesRepository.class)
                .buscarTodos(DistribucionDeVianda.class).stream()
                .filter(d -> d.getFechaAlta().isAfter(fechaInicio))
                .toList();
        Map<Integer, Integer> viandasPorHeladera = new HashMap<>();
        distribuciones.forEach(distribucion -> {
            Integer heladeraId = Math.toIntExact(retiradas ? distribucion.getHeladeraOrigen().getId() : distribucion.getHeladeraDestino().getId());
            viandasPorHeladera.merge(heladeraId, distribucion.getCantidadDeViandasAMover(), Integer::sum);
        });
        return viandasPorHeladera;
    }

    private static Map<Integer, Integer> obtenerDonacionesVianda(LocalDateTime fechaInicio) {
        List<DonacionDeVianda> donaciones = ServiceLocator.instanceOf(ColaboracionesRepository.class)
                .buscarTodos(DonacionDeVianda.class).stream()
                .filter(d -> d.getFechaAlta().isAfter(fechaInicio))
                .toList();
        Map<Integer, Integer> viandasPorColaborador = new HashMap<>();
        donaciones.forEach(donacion -> viandasPorColaborador.merge((int) donacion.getColaborador().getId(), donacion.getCantidad(), Integer::sum));
        return viandasPorColaborador;
    }

    public static void main(String[] args) throws IOException {
        // TODO ACA TIENE QUE IR EL REPORTE QUE SE GENERE CON EL CRONJOB
        GeneradorDeReportes.realizarReporteSemanal("src/main/resources/public/reportes/reporte_semanal.txt");
    }
}
