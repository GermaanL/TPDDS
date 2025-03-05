package ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaJuridica;

import ar.edu.utn.frba.dds.domain.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
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
@Table(name = "colocaciones_de_heladera")
public class ColocacionHeladera extends Colaboracion {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "heladeraColocada_id", referencedColumnName = "id")
    private Heladera heladeraColocada;

    /*
    public void inaugurarHeladera() throws IOException {
        heladera.setEstado(new HeladeraActiva(LocalDateTime.now()));
        heladera.setFechaInauguracion(LocalDate.now());
        //heladera.iniciarControlTemperatura();
        //TODO iniciar control de movimiento
        heladera.contadorDeMeses();
        heladera.iniciarRecepcionDePermisosApertura();
    }
    */

    @Override
    public void realizar() {
        //TODO
    }
}
