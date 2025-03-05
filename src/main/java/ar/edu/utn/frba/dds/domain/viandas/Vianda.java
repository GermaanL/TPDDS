package ar.edu.utn.frba.dds.domain.viandas;

import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "viandas")
public class Vianda {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "fechaCaducidad", columnDefinition = "DATE")
    private LocalDate fechaCaducidad;

    @Column(name = "estaVencida")
    private Boolean estaVencida = false;

    @Column(name = "fechaDonacion", columnDefinition = "DATE")
    private LocalDate fechaDonacion;

    @ManyToOne
    @JoinColumn(name = "donadaPorColaborador_id", referencedColumnName = "id")
    private Colaborador donadaPor;

    @ManyToOne
    @JoinColumn(name = "heladera_id", referencedColumnName = "id")
    private Heladera heladeraDondeEsta;

    @Column(name = "caloriasEnKcal")
    private Integer caloriasEnKcal;

    @Column(name = "pesoEnKg")
    private Double pesoEnKg;

    @Column(name = "entregada")
    private Boolean entregada = false;

    public void marcarComoEntregada(){
        this.setEntregada(true);
    }

    public void revisarVencimiento() {
        if(LocalDate.now().isAfter(this.getFechaCaducidad())) this.setEstaVencida(true);
    }

}
