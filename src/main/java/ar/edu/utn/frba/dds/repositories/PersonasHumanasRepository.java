package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.domain.colaboradores.PersonaHumana;

import java.util.Optional;

public class PersonasHumanasRepository extends BaseRepository{

    public Optional<PersonaHumana> findByTipoDocAndDocumento(String tipoDoc, String documento) {
        return buscarTodos(PersonaHumana.class).stream()
                .filter(persona -> persona.getDocumento().getTipoDocumento().name().equals(tipoDoc) &&
                        persona.getDocumento().getNumeroDocumento().equals(documento))
                .findFirst();
    }
}
