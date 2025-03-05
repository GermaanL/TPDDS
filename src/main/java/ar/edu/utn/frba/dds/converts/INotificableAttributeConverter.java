package ar.edu.utn.frba.dds.converts;

import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import ar.edu.utn.frba.dds.domain.colaboradores.PersonaHumana;
import ar.edu.utn.frba.dds.domain.colaboradores.PersonaJuridica;
import ar.edu.utn.frba.dds.domain.notificaciones.INotificable;
import ar.edu.utn.frba.dds.domain.personasVulnerables.PersonaVulnerable;
import ar.edu.utn.frba.dds.domain.tarjetas.ITitularDeTarjeta;
import ar.edu.utn.frba.dds.domain.tecnicos.Tecnico;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)

public class INotificableAttributeConverter implements AttributeConverter<INotificable, String> {

    @Override
    public String convertToDatabaseColumn(INotificable notificable) {
        return null;
    }

    @Override
    public INotificable convertToEntityAttribute(String valor) {
        return null;
    }
}


