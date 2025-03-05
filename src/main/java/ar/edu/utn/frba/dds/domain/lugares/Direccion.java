package ar.edu.utn.frba.dds.domain.lugares;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {

    @Column(name = "calle")
    private String calle;

    @Column(name = "altura")
    private Integer altura;

    @Column(name = "codigoPostal")
    private Integer codigoPostal;

    @Column(name = "provincia")
    private String provincia;

    @Column(name = "localidad")
    private String localidad;

    public String direccionCompleta(){
        return getCalle() + " "+ getAltura().toString() + ", " +  getCodigoPostal();
    }
}
