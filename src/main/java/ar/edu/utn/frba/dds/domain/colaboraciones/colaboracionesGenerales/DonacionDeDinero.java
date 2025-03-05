package ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesGenerales;

import ar.edu.utn.frba.dds.domain.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.domain.colaboraciones.FrecuenciaDonacion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "donaciones_de_dinero")
public class DonacionDeDinero extends Colaboracion{

    @Column(name = "monto")
    private Integer monto;

    @Enumerated(EnumType.STRING)
    @Column(name = "frecuencia")
    private FrecuenciaDonacion frecuencia;

    @Override
    public void realizar() {
        //TODO
        //Aún no nos corresponde desarrollar este método
    }
}
