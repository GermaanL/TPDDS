package ar.edu.utn.frba.dds.domain.personasVulnerables;

import ar.edu.utn.frba.dds.domain.documentos.DocumentoDeIdentidad;
import ar.edu.utn.frba.dds.domain.tarjetas.ITitularDeTarjeta;
import ar.edu.utn.frba.dds.domain.colaboradores.PersonaHumana;
import ar.edu.utn.frba.dds.domain.lugares.Direccion;
import ar.edu.utn.frba.dds.domain.tarjetas.Tarjeta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "personas_vulnerables")
public class PersonaVulnerable  {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "fechaNacimiento", columnDefinition = "DATE")
    private LocalDate fechaNacimiento ;

    @Embedded
    private DocumentoDeIdentidad documento;

    @Column(name = "fechaAlta", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaAlta;

    @Column(name = "enSituacionDeCalle")
    private Boolean enSituacionDeCalle ;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitud", column = @Column(insertable = false, updatable = false)),
            @AttributeOverride(name = "longitud", column = @Column(insertable = false, updatable = false))
    })
    private Direccion domicilio ;

    @Column(name = "cantidadMenoresACargo")
    private Integer cantidadMenoresACargo;

    @ManyToOne
    @JoinColumn(name = "registradoPorPersonaHumana_id")
    private PersonaHumana registradoPor;

    @OneToOne
    @JoinColumn(name = "tarjeta_id", referencedColumnName = "id")
    protected Tarjeta tarjeta;

    /*
    public void usarTarjeta(Heladera unaHeladera){
        if (tarjeta.tieneUsosDisponibles()){
            LocalDate hoy = LocalDate.now();
            UsoDeTarjetaPersonaVulnerable uso = new UsoDeTarjetaPersonaVulnerable(this.tarjeta, hoy, unaHeladera);
            this.tarjeta.agregarUso(uso);
            unaHeladera.consumirHeladera();
        }
        else{
            throw new NoTieneUsosDisponiblesException("La tarjeta con código "+ this.tarjeta.getCodigo() + " no posee mas usos disponibles por el dia de hoy");
        }
    }
     */
}
