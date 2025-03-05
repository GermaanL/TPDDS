package ar.edu.utn.frba.dds.utils.notificadores;

import ar.edu.utn.frba.dds.domain.contactos.MedioDeContacto;
import ar.edu.utn.frba.dds.domain.notificaciones.INotificable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notificador {

    private INotificadorAdapter adapter;

    public static Notificador of(MedioDeContacto unMedio){
        switch(unMedio){
            case TELEGRAM -> {
                TelegramBot bot = BotFactory.getInstance("<BotToken>", "<BotUsername>");
                return new Notificador(new TelegramAdapter(bot));
            }
            case MAIL -> {return new Notificador(new MailAdapter());
            }
            case WHATSAPP -> {return new Notificador(new WhatsAppAdapter());
            }
            default -> {return null;
            }
        }
    }

    public void notificar(INotificable unNotificable, String contenido){
        this.getAdapter().notificar(unNotificable, contenido);
    };

    public void notificarATodos(List<INotificable> listaDeNotificables, String contenido) {
        listaDeNotificables.parallelStream().forEach(notificable -> this.notificar(notificable, contenido));
    }
}
