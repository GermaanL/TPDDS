package ar.edu.utn.frba.dds.domain.colaboraciones;

import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Colaboracion {

    @Id
    @GeneratedValue
    protected Long id;

    @Column(name = "fechaAlta", columnDefinition = "TIMESTAMP")
    protected LocalDateTime fechaAlta;

    @ManyToOne

    @JoinColumn(name = "formaDeColaboracion_id", referencedColumnName = "id")
    protected FormaDeColaboracion tipo;

    @ManyToOne
    @JoinColumn(name = "colaborador_id")
    protected Colaborador colaborador;

    @Column(name = "pendiente")
    protected boolean pendiente;

    public abstract void realizar();
}
