package ar.edu.utn.frba.dds.repositories.Daos;

import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDAO {
   <T> void create (T objeto);
   <T> Optional<T> find (long id, Class<T> clase);
   <T> Optional<T> find (String id, Class<T> clase);
   <T> void update (T objeto);
   <T> void delete (T objeto);
   <T> List<T> findAll(Class<T> clase);
}
