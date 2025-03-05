package ar.edu.utn.frba.dds.server.framework;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PrettyProperties {
    private static PrettyProperties instance = null;
    private Properties prop;

    // Obtener la instancia única de PrettyProperties (Singleton)
    public static PrettyProperties getInstance() {
        if (instance == null) {
            instance = new PrettyProperties();
        }
        return instance;
    }

    // Constructor privado para la clase Singleton
    private PrettyProperties() {
        this.prop = new Properties();
        this.readProperties();
    }

    // Cargar el archivo de propiedades desde el classpath
    private void readProperties() {
        try {
            // Usar el ClassLoader para obtener el archivo desde el classpath
            InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties");

            // Verificar si el archivo fue encontrado
            if (input == null) {
                System.out.println("No se pudo encontrar config.properties en el classpath.");
                return; // Salir si el archivo no está disponible
            }

            // Cargar las propiedades
            this.prop.load(input);
        } catch (IOException ex) {
            System.out.println("Error al cargar el archivo de propiedades.");
            ex.printStackTrace(); // Imprimir el stack trace en caso de error
        }
    }

    // Obtener el valor de una propiedad por su nombre
    public String propertyFromName(String name) {
        return this.prop.getProperty(name, null);
    }
}

