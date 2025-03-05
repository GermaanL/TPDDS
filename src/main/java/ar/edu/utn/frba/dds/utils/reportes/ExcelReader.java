package ar.edu.utn.frba.dds.utils.reportes;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Map;

public class ExcelReader {

    public static void procesarFallasTecnicas(String filePath, LocalDateTime fechaLimite, Map<Integer, Long> fallasPorHeladera) throws IOException {
        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Saltar la fila de encabezado

                LocalDateTime fecha = row.getCell(1).getLocalDateTimeCellValue();

                if (fecha.toLocalDate().isAfter(ChronoLocalDate.from(fechaLimite))) {
                    int heladeraId = (int) row.getCell(2).getNumericCellValue();
                    fallasPorHeladera.put(heladeraId, fallasPorHeladera.getOrDefault(heladeraId, 0L) + 1);
                }
            }
        }
    }

    public static void procesarDonacionesVianda(String filePath, LocalDateTime fechaLimite, Map<Integer, Integer> viandasPorColaborador) throws IOException {
        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Saltar la fila de encabezado

                LocalDateTime fecha = row.getCell(1).getLocalDateTimeCellValue();
                if (fecha.toLocalDate().isAfter(ChronoLocalDate.from(fechaLimite))) {
                    int colaboradorId = (int) row.getCell(2).getNumericCellValue();
                    int cantidad = (int) row.getCell(3).getNumericCellValue();
                    viandasPorColaborador.put(colaboradorId, viandasPorColaborador.getOrDefault(colaboradorId, 0) + cantidad);
                }
            }
        }
    }

    public static void procesarDistribucionesVianda(String filePath, LocalDateTime fechaLimite, Map<Integer, Integer> viandasRetiradasPorHeladera, Map<Integer, Integer> viandasColocadasPorHeladera) throws IOException {
        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Saltar la fila de encabezado

                LocalDateTime fecha = row.getCell(1).getLocalDateTimeCellValue();
                if (fecha.toLocalDate().isAfter(ChronoLocalDate.from(fechaLimite))) {
                    int heladeraOrigenId = (int) row.getCell(2).getNumericCellValue();
                    int cantidad = (int) row.getCell(3).getNumericCellValue();
                    int heladeraDestinoId = (int) row.getCell(4).getNumericCellValue();

                    viandasRetiradasPorHeladera.put(heladeraOrigenId, viandasRetiradasPorHeladera.getOrDefault(heladeraOrigenId, 0) + cantidad);
                    viandasColocadasPorHeladera.put(heladeraDestinoId, viandasColocadasPorHeladera.getOrDefault(heladeraDestinoId, 0) + cantidad);
                }
            }
        }
    }
}
