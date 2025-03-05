package ar.edu.utn.frba.dds.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileWriter {

  public static void write(String unString, String unPath) { //tener en cuanta de poner la extension del archivo
    try {
      java.io.FileWriter out = new java.io.FileWriter(unPath, true);
      out.write(unString);
      out.close();
    }
    catch (IOException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  public static void writeXlsx(List<List<String>> data, String path){
    Workbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet("Hoja1");

    int rowNum = 0;

    for (List<String> rowData : data) {
      Row row = sheet.createRow(rowNum++);
      int colNum = 0;

      for (String field : rowData) {
        Cell cell = row.createCell(colNum++);
        cell.setCellValue(field);
      }
    }

    try (FileOutputStream outputStream = new FileOutputStream(path)) {
      workbook.write(outputStream);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void underXlsx(List<List<String>> data, String path){
    try {
      FileInputStream file = new FileInputStream(new File(path));
      Workbook workbook = new XSSFWorkbook(file);
      Sheet sheet = workbook.getSheetAt(0);

      int rowNum = sheet.getLastRowNum() + 1;

      for (List<String> rowData : data) {
        Row row = sheet.createRow(rowNum++);
        int colNum = 0;

        for (String field : rowData) {
          Cell cell = row.createCell(colNum++);
          cell.setCellValue(field);
        }
      }

      file.close();

      FileOutputStream outputStream = new FileOutputStream(path);
      workbook.write(outputStream);
      outputStream.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void continueXlsx(List<List<String>> data, String path){
    try {
      FileInputStream file = new FileInputStream(new File(path));
      Workbook workbook = new XSSFWorkbook(file);
      Sheet sheet = workbook.getSheetAt(0);

      int lastColumnIndex = -1;
      for (Row row : sheet) {
        lastColumnIndex = Math.max(lastColumnIndex, row.getLastCellNum());
      }

      int rowNum = 0;
      for (List<String> rowData : data) {
        Row row = sheet.getRow(rowNum++);
        if (row == null) {
          row = sheet.createRow(rowNum - 1);
        }

        int colNum = lastColumnIndex;
        for (String field : rowData) {
          Cell cell = row.createCell(colNum++);
          cell.setCellValue(field);
        }
      }

      file.close();

      FileOutputStream outputStream = new FileOutputStream(path);
      workbook.write(outputStream);
      outputStream.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public static void main(String[] args) {
    //esto que te va a escribir en el txt si del bloque de nota lo pegas en un excel se pega cada cosa en una celda
    FileWriter.write("columna1 \t columna2 \t columna3 \n dato1 \t dato2 \t dato3 \n", "src/main/java/ar/edu/utn/frba/dds/utils/prueba.txt");

      List<List<String>> data = Arrays.asList(
          Arrays.asList("Columna7", "Columna8", "Columna8"),
          Arrays.asList("Dato13", "Dato14", "Dato15"),
          Arrays.asList("Dato16", "Dato17", "Dato18"));

    //FileWriter.writeXlsx(data, "src/main/java/ar/edu/utn/frba/dds/utils/prueba.xlsx");
    //FileWriter.underXlsx(data, "src/main/java/ar/edu/utn/frba/dds/utils/prueba.xlsx");
    //FileWriter.continueXlsx(data, "src/main/java/ar/edu/utn/frba/dds/utils/prueba.xlsx");
  }
}
