package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.domain.suscripciones.Suscripcion;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HandlebarsHelpers {

  public static void registerHelpers(Handlebars handlebars) {
    handlebars.registerHelper("formatDate", new Helper<LocalDateTime>() {
      @Override
      public CharSequence apply(LocalDateTime date, Options options) {
        if (date == null) {
          return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");
        return date.format(formatter);
      }
    });

    handlebars.registerHelper("eq", new Helper<Object>() {
      @Override
      public CharSequence apply(Object value1, Options options) {
        Object value2 = options.param(0); // Obtiene el segundo parámetro
        return (value1 != null && value1.equals(value2)) ? "selected" : ""; // Retorna "selected" si son iguales
      }
    });

    handlebars.registerHelper("eq2", new Helper<Object>() {
      @Override
      public CharSequence apply(Object value1, Options options) throws IOException {
        Object value2 = options.param(0); // Obtiene el segundo parámetro
        if (value1 != null && value1.equals(value2)) {
          return options.fn(options.context); // Si son iguales, ejecuta el bloque de código dentro de {{#eq2}}
        } else {
          return options.inverse(options.context); // Si no son iguales, ejecuta el bloque {{else}}
        }
      }
    });

    handlebars.registerHelper("isType", new Helper<Object>() {
      @Override
      public CharSequence apply(Object suscripcion, Options options) throws IOException {
        String tipo = options.param(0); // Obtiene el tipo de suscripción a comparar
        if (suscripcion instanceof Suscripcion && ((Suscripcion) suscripcion).getTipo().equals(tipo)) {
          return options.fn(options.context); // Si coincide, ejecuta el bloque {{#isType}}
        } else {
          return options.inverse(options.context); // Si no, ejecuta {{else}}
        }
      }
    });
  }
}
