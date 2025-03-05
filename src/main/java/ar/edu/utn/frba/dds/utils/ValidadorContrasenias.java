package ar.edu.utn.frba.dds.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.regex.Pattern;
public class ValidadorContrasenias {

  private static String ARCHIVO_CONTRASENIAS_DEBIL = "src/main/java/ar/edu/utn/frba/dds/utils/ContraseniasDebiles.txt"; // Ruta al archivo

  public static boolean permitirContrasenia(String contrasenia){
    return esContraseniaSegura(contrasenia) && esContraseniaValidaSinUnicode(contrasenia) && esContraseniaValidaSinSecuencias(contrasenia);
  }

  public static boolean esContraseniaValida(String unaContrasenia){
    return esContraseniaLarga(unaContrasenia) && tieneAlMenosUnaMayusculaYUnaMinuscula(unaContrasenia) && tieneAlMenosUnNumero(unaContrasenia) && tieneSimboloEspecial(unaContrasenia);
  }

  public static boolean esContraseniaLarga( String contrasenia){
    return contrasenia.length() >= 8;
  }

  public static boolean tieneAlMenosUnaMayusculaYUnaMinuscula(String contrasenia){
    boolean tieneMayuscula = false;
    boolean tieneMinuscula = false;

    for (char caracter : contrasenia.toCharArray()){
      if (Character.isUpperCase(caracter)){
        tieneMayuscula =true;
        break;
      }
    }

    for (char caracter : contrasenia.toCharArray()){
      if (Character.isLowerCase(caracter)){
        tieneMinuscula=true;
        break;
      }

    }
    return tieneMayuscula && tieneMinuscula;
  }

  public static boolean tieneAlMenosUnNumero(String contrasenia){
    boolean tieneNumero = false;

    for (char caracter : contrasenia.toCharArray()){
      if (Character.isDigit(caracter)){
        tieneNumero=true;
        break;
      }
    }
    return tieneNumero;
  }

  public static boolean tieneSimboloEspecial(String contrasenia){
    boolean tieneSimbolo = false;
    for (char caracter : contrasenia.toCharArray()) {
      if (!Character.isLetterOrDigit(caracter) && !Character.isWhitespace(caracter)) {
        tieneSimbolo = true;
        break;
      }
    }
    return tieneSimbolo;
  }

  public static boolean esContraseniaSegura(String unaContrasenia){
    return esContraseniaValida(unaContrasenia) && !estaPresenteEnArchivoDeContraseniasComunes(unaContrasenia, ARCHIVO_CONTRASENIAS_DEBIL);
  }

  public static boolean estaPresenteEnArchivoDeContraseniasComunes(String contrasenia, String pathArchivo){

    try (BufferedReader lector = new BufferedReader(new FileReader(ARCHIVO_CONTRASENIAS_DEBIL))) {
      String linea;
      while ((linea = lector.readLine()) != null) {
        if(Objects.equals(contrasenia, linea))
          return true;
      }
    }
    catch (IOException e) {
      System.err.println(e.getMessage());
      return false;
    }
    return false;
  }

  public static boolean esContraseniaValidaSinUnicode(String contrasenia) {
    if (contrasenia.isEmpty()) {
      return false;
    }

    // Comprobar si la contrasenia contiene solo caracteres ASCII
    return Pattern.matches("\\p{ASCII}+", contrasenia); // Se usa una expresion regular
  }

  public static boolean esContraseniaValidaSinSecuencias(String contrasenia) {
    return !contrasenia.isEmpty() && !tieneSecuencias(contrasenia);
  }

  public static boolean tieneSecuencias (String unaContrasenia){
    int contadorRepetidos = 0;
    for (int i = 0; i < unaContrasenia.length(); i++) {
      char caracterActual = unaContrasenia.charAt(i);
      if (i > 0 && caracterActual == unaContrasenia.charAt(i - 1)) {
        contadorRepetidos++;
      } else {
        contadorRepetidos = 0;
      }

      if (contadorRepetidos >= 2) {
        return true;
      }
    }

    return false;
  }

}
