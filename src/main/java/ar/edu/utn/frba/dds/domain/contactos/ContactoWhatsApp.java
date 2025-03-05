package ar.edu.utn.frba.dds.domain.contactos;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class ContactoWhatsApp {

    @Embedded
    @Column(name = "nroTelefono")
    @Getter
    public String nroTelefono;
}
