package ar.edu.utn.frba.dds.domain.tarjetas;

import ar.edu.utn.frba.dds.utils.GeneradorDeCodigo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Setter@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipoTitular")
public abstract class Tarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "codigoIdentificador")
    protected String codigoIdentificador;

    @Column(name = "fechaAlta", columnDefinition = "TIMESTAMP")
    protected LocalDateTime fechaAlta;

    @Column(name = "fechaEntrega", columnDefinition = "DATE")
    protected LocalDate fechaEntrega;

    @Setter
    @Enumerated(EnumType.STRING)
    protected EstadoTarjeta estado_actual;

    /*
    @OneToMany(mappedBy = "tarjetaPersonaVulnerable")
    @JoinColumn(name = "tarjeta_id", referencedColumnName = "id")
    protected List<UsoDeTarjeta> historialDeUsos;
    */

    public abstract Boolean puedeUsarse();
    public abstract void usar();

}
