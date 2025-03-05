package ar.edu.utn.frba.dds.domain.colaboraciones;

import java.io.IOException;

public interface IContribuible {
    void realizarContribucion() throws IOException;

    String getTipo();
}
