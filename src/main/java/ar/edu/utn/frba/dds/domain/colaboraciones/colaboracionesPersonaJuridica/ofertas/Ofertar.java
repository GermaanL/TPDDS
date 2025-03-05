package ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaJuridica.ofertas;

import ar.edu.utn.frba.dds.domain.colaboraciones.Colaboracion;
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
@Table(name = "ofertar")
public class Ofertar extends Colaboracion {

    @Id
    @GeneratedValue
    protected Long id;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "ofertas_id", referencedColumnName = "id")
    private Oferta oferta;

    @Override
    public void realizar() {
        //TODO
    }

}
