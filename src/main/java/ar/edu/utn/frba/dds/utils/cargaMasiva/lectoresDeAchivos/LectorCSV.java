package ar.edu.utn.frba.dds.utils.cargaMasiva.lectoresDeAchivos;

import ar.edu.utn.frba.dds.domain.documentos.TipoDoc;
import lombok.SneakyThrows;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LectorCSV implements ILectorArchivo{

  @SneakyThrows
  public List<ColaboracionLeidaDTO> leerArhcivo(String pathArchivo){
    List<ColaboracionLeidaDTO> colaboracionLeidas = new ArrayList<>();

    try (
        Reader reader = new FileReader(pathArchivo);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
    ) {
      for (CSVRecord csvRecord : csvParser) {

        String tipoDoc = csvRecord.get("Tipo Doc");
        String documento = csvRecord.get("Documento");
        String nombre = csvRecord.get("Nombre");
        String apellido = csvRecord.get("Apellido");
        String mail = csvRecord.get("Mail");
        String fechaDeColaboracion = csvRecord.get("Fecha de colaboracion");
        String formaDeColaboracion = csvRecord.get("Forma de colaboracion");
        String cantidad = csvRecord.get("Cantidad");

        TipoDoc unTipo = TipoDoc.valueOf(tipoDoc);
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate unaFechaDeColaboracion = LocalDate.parse(fechaDeColaboracion, formatoFecha);

        ColaboracionLeidaDTO colaboracionLeidaDTO = new ColaboracionLeidaDTO(unTipo, documento, nombre, apellido, mail, unaFechaDeColaboracion,formaDeColaboracion, Integer.parseInt(cantidad));

        colaboracionLeidas.add(colaboracionLeidaDTO);
      }

    }
    catch (IOException e) {
      e.printStackTrace();
    }

    return colaboracionLeidas;
  }

  public static void main(String[] args){
    LectorCSV lectorCSV = new LectorCSV();
    List<ColaboracionLeidaDTO> colaboracionLeidas = lectorCSV.leerArhcivo("src/test/java/ar/edu/utn/frba/dds/utilsTests/CSV_PRUEBAS.csv");
  }
}
