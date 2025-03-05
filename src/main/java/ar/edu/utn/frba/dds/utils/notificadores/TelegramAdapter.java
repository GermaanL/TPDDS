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
public class TelegramAdapter implements INotificadorAdapter {

    private TelegramBot servicioAdaptado;

    @Override
    public void notificar(INotificable unNotificable, String contenido) {
        Contacto contacto = unNotificable.obtenerInfoDeContacto();
        long chatId = Long.parseLong(contacto.getValor());
        this.servicioAdaptado.sendMessageToUser(chatId, contenido);
    }
}
