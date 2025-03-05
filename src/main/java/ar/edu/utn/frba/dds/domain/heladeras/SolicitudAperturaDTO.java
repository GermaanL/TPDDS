package ar.edu.utn.frba.dds.domain.heladeras;

import lombok.Getter;

import java.time.LocalDateTime;

public class SolicitudAperturaDTO {

    @Getter
    private Long idHeladera;
    @Getter
    private LocalDateTime fechaHoraCreacion;
    @Getter
    private LocalDateTime fechaHoraVencimiento;
    @Getter
    private String codigoIdentificador;

    public SolicitudAperturaDTO(Long idHeladera, String codigoIdentificador, LocalDateTime fechaHoraCreacion, LocalDateTime fechaHoraVencimiento, Long id) {
        this.idHeladera = idHeladera;
        this.codigoIdentificador = codigoIdentificador;
        this.fechaHoraCreacion = fechaHoraCreacion;
        this.fechaHoraVencimiento = fechaHoraVencimiento;
    }


}
