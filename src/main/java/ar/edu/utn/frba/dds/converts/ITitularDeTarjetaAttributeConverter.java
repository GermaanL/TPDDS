package ar.edu.utn.frba.dds.converts;

import ar.edu.utn.frba.dds.domain.colaboradores.PersonaHumana;
import ar.edu.utn.frba.dds.domain.colaboradores.PersonaJuridica;
import ar.edu.utn.frba.dds.domain.personasVulnerables.PersonaVulnerable;
import ar.edu.utn.frba.dds.domain.tarjetas.ITitularDeTarjeta;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

//@Converter(autoApply = true)
//public class ITitularDeTarjetaAttributeConverter implements AttributeConverter<ITitularDeTarjeta, Long> {
//
//    @Override
//    public Long convertToDatabaseColumn(ITitularDeTarjeta tipo) {
//        if (tipo instanceof PersonaHumana) {
//            return ((PersonaHumana) tipo).getId();
//        } else if (tipo instanceof PersonaJuridica) {
//            return ((PersonaJuridica) tipo).getId();
//        } else if (tipo instanceof PersonaVulnerable) {
//            return ((PersonaVulnerable) tipo).getId();
//        }
//        return null;
//    }
//
//    @Override
//    public ITitularDeTarjeta convertToEntityAttribute(Long valor) {
//        if ("PersonaHumana".equals(valor)) {
//            return new PersonaHumana();
//        } else if ("PersonaJuridica".equals(valor)) {
//            return new PersonaJuridica();
//         }else if ("PersonaVulnerable".equals(valor)) {
//            return new PersonaVulnerable();
//        }
//        return null;
//    }
//}