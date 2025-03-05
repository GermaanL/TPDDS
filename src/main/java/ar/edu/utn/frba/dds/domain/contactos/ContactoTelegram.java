package ar.edu.utn.frba.dds.domain.contactos;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
public class ContactoTelegram {

    @Column(name = "telegram_chat_id")
    Long chatId;
}
