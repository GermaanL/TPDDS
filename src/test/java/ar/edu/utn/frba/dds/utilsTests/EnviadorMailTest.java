package ar.edu.utn.frba.dds.utilsTests;

import ar.edu.utn.frba.dds.utils.notificadores.EnviadorMail;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class EnviadorMailTest {

    @Test
    public void testEnvioExitoso() {
        String destinatario = "timuyejoppi-1838@yopmail.com";
        String asunto = "Prueba de Envío de Correo";
        String mensaje = "Este es un mensaje de prueba para verificar el funcionamiento del EnviadorMail.";
        String emisor = "tpanualdisenogruposiete@gmail.com";
        String contrasenia = "<pw>";

        Assertions.assertDoesNotThrow(() -> {
            EnviadorMail.enviarMail(destinatario, asunto, mensaje, emisor, contrasenia);
        });
    }

    @Test
    public void testErrorAutenticacion() {
        String destinatario = "email-invalido";
        String asunto = "Prueba de Envío de Correo con Contraseña Incorrecta";
        String mensaje = "Este es un mensaje de prueba para verificar el manejo de errores del EnviadorMail.";
        String emisor = "tpanualdisenogruposiete@gmail.com";
        String contrasenia = "<pw>"; // Contraseña incorrecta

        Exception exception = Assertions.assertThrows(MessagingException.class, () -> {
            EnviadorMail.enviarMail(destinatario, asunto, mensaje, emisor, contrasenia);
        });

        Assertions.assertTrue(exception.getMessage().contains("Invalid Addresses"));
    }
}
