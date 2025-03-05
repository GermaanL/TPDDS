package ar.edu.utn.frba.dds.utils.notificadores;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.Getter;

public class WhatsAppSender {
  public static final String ACCOUNT_SID = "<AccSID>"; //datos privados
  public static final String AUTH_TOKEN = "<AuthToken>";//datos privados
  @Getter
  private final String emisor = "whatsapp:+";

  /*
  public static void enviarWpp (String desde, String hacia, String mensaje, String ACCOUNT_SID, String AUTH_TOKEN){
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message message = Message.creator(
        new PhoneNumber("whatsapp:"+hacia),
        new PhoneNumber("whatsapp:"+desde),
        mensaje)
        .create();

    System.out.println(message.getSid());
  }
  */

  public void enviarWpp (String desde, String hacia, String mensaje, String ACCOUNT_SID, String AUTH_TOKEN){
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message message = Message.creator(
                    new PhoneNumber("whatsapp:"+hacia),
                    new PhoneNumber("whatsapp:"+desde),
                    mensaje)
            .create();

    System.out.println(message.getSid());
  }

  public static void main(String[] args) {
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message message = Message.creator(
            new PhoneNumber("whatsapp:+"),
            new PhoneNumber("whatsapp:+"),
            "Whatsapp enviado desde Java")
        .create();

    System.out.println(message.getSid());
  }

}