package ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaHumana;

import ar.edu.utn.frba.dds.domain.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.domain.viandas.Vianda;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "donaciones_de_viandas")
public class DonacionDeVianda extends Colaboracion {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "vianda_id", referencedColumnName = "id") // VIANDA_ID -> CAMBIAR
    private Vianda vianda;

    @Column (name = "cantidad")
    private Integer cantidad;

    /*
    public void viandasAHeladera(){
        this.getViandas().forEach(vianda ->{
            heladera.ingresarVianda(vianda);
            vianda.setHeladeraDondeEsta(heladera);
        });
    }
    */

    @Override
    public void realizar() {
        //TODO

    }
}
