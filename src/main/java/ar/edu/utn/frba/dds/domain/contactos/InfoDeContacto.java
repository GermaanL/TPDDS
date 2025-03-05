package ar.edu.utn.frba.dds.domain.contactos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Setter
@Getter
@Embeddable
public class InfoDeContacto {

    @Embedded
    ContactoTelegram contactoTelegram;

    @Embedded
    ContactoWhatsApp contactoWhatsApp;

    @Embedded
    ContactoMail contactoMail;
}
