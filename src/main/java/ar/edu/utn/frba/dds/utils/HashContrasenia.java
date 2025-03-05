package ar.edu.utn.frba.dds.utils;

import org.mindrot.jbcrypt.BCrypt;

public class HashContrasenia {

    public static String generarHash(String contrasenia) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(contrasenia, salt);
    }

    public static boolean verificarContrasenia(String contrasenia, String hashContrasenia) {
        return BCrypt.checkpw(contrasenia, hashContrasenia);
    }

    public static void main(String[] args) {
        String contrasenia = "123";
        String hash = generarHash(contrasenia);
        System.out.println("Hash: " + hash);
    }
}