package ar.edu.utn.frba.dds.utils.notificadores;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.Properties;

//para este util se usará un correo ficticio con las credenciales contra: Hola2024 y address: tpanualdisenogruposiete@gmail.com y appPasword: obck nzgq xmvy uiod
public class EnviadorMail {

    @Getter
    private static final String mailONG = "tpanualdisenogruposiete@gmail.com";
    @Getter
    private static final String appPassword = "<Pw>";

    @SneakyThrows
  public static void enviarMail(String destinatario, String asunto, String mensaje, String emisor , String contrasenia ) throws MessagingException {

      // Configurar propiedades
      Properties props = new Properties();
      props.put("mail.smtp.host", "smtp.gmail.com");
      props.put("mail.smtp.port", "587");
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");

      Session session = Session.getInstance(props, new Authenticator() {
        @Override
        protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
          String emisorFinal = emisor.isEmpty() ? mailONG : emisor;
          String contraseniaFinal = contrasenia.isEmpty() ? appPassword : contrasenia;

          return new jakarta.mail.PasswordAuthentication(emisorFinal, contraseniaFinal);

        }
      });

      try {

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("tpanualdisenogruposiete@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        message.setSubject(asunto);
        message.setText(mensaje);

        Transport.send(message);

        System.out.println("El correo ha sido enviado correctamente a " + destinatario);

      } catch (MessagingException e) {
        System.out.println("Error al enviar el correo: " + e.getMessage());
        throw e;
      }
    }

  public static void enviarMail(String destinatario, String asunto, String mensaje) throws MessagingException {
    enviarMail(destinatario, asunto, mensaje, "", "");
  }

  public static void main(String[] args) throws MessagingException {

    EnviadorMail.enviarMail("<dest>", "mail desde java", "hola desde java \n -Grupo 7 DDS desde Java");
  }
}


