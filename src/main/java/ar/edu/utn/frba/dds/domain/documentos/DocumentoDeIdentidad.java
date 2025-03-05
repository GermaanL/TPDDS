package ar.edu.utn.frba.dds.domain.documentos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DocumentoDeIdentidad {

    @Enumerated(EnumType.STRING)
    private TipoDoc tipoDocumento;

    @Column(name = "documento")
    private String numeroDocumento;
}
