package ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaJuridica;

import ar.edu.utn.frba.dds.domain.colaboraciones.IContribuible;
import java.io.IOException;

public interface IContribuiblePorPersonaJuridica extends IContribuible {
    void realizarContribucion() throws IOException;
}
