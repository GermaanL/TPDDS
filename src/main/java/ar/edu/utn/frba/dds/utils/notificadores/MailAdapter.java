package ar.edu.utn.frba.dds.utils.notificadores;

import ar.edu.utn.frba.dds.domain.contactos.Contacto;
import ar.edu.utn.frba.dds.domain.notificaciones.INotificable;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailAdapter implements INotificadorAdapter {

    private EnviadorMail servicioAdaptado;

    @Override
    public void notificar(INotificable unNotificable, String contenido) {
        Contacto contacto = unNotificable.obtenerInfoDeContacto();
        String destinatario = contacto.getValor();
        String asunto = "Notificación Recibida";
        try {
            this.servicioAdaptado.enviarMail(destinatario, asunto, contenido, "", "");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
