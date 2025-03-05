package ar.edu.utn.frba.dds.domain.heladeras;

import ar.edu.utn.frba.dds.domain.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import ar.edu.utn.frba.dds.domain.tarjetas.Tarjeta;
import ar.edu.utn.frba.dds.domain.viandas.Vianda;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "solicitudes_apertura_heladera")
public class SolicitudApertura {

    @Id
    @GeneratedValue
    private Long id;

    /*
    @Column(name = "idHeladera")
    private Long idHeladera;
    */

    @ManyToOne
    @JoinColumn(name = "heladera_id", referencedColumnName = "id")
    private Heladera heladeraSolicitada;

    @Column(name = "fechaHoraCreacion", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaHoraCreacion;

    @Column(name = "fechaHoraVencimiento", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaHoraVencimiento;

    @ManyToOne
    @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
    private Colaborador colaborador;

    @ManyToOne
    @JoinColumn(name = "tarjeta_id", referencedColumnName = "id")
    private Tarjeta tarjetaAUsar;

    @Enumerated(EnumType.STRING)
    private MotivoApertura motivoApertura;

    @Column(name = "fueRealizada")
    private Boolean fueRealizada;

    @OneToOne
    @JoinColumn(name = "colaboracion_id", referencedColumnName = "id")
    private Colaboracion colaboracion;

    public Boolean estaVencida(){
        return LocalDateTime.now().isAfter(this.getFechaHoraVencimiento());
    }

    public SolicitudAperturaDTO convertToDTO(SolicitudApertura solicitudApertura){
        return new SolicitudAperturaDTO(
            solicitudApertura.getHeladeraSolicitada().getId(),
            solicitudApertura.getTarjetaAUsar().getCodigoIdentificador(),
            solicitudApertura.fechaHoraCreacion,
            solicitudApertura.fechaHoraVencimiento,
            solicitudApertura.getColaboracion().getId()
        );
    }
}
