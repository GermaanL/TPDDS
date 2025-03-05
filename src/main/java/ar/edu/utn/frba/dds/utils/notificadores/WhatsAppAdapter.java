package ar.edu.utn.frba.dds.utils.notificadores;

import ar.edu.utn.frba.dds.domain.contactos.Contacto;
import ar.edu.utn.frba.dds.domain.notificaciones.INotificable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WhatsAppAdapter implements INotificadorAdapter {

    private WhatsAppSender servicioAdaptado;

    @Override
    public void notificar(INotificable unNotificable, String contenido) {
        Contacto contacto = unNotificable.obtenerInfoDeContacto();
        String hacia = "whatsapp:" + contacto.getValor();
        this.servicioAdaptado.enviarWpp(servicioAdaptado.getEmisor(), hacia, contenido, WhatsAppSender.ACCOUNT_SID, WhatsAppSender.AUTH_TOKEN);
    }
}
