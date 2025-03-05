package ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaJuridica.ofertas;

import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ofertas")
public class Oferta {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "rubro")
    private String rubro;

    @Column(name = "puntosNecesarios")
    private Integer puntosNecesarios;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "imagen")
    private String imagen;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "canjeado")
    private Integer canjeado;

    @Column(name = "SinStock")
    private Boolean SinStock;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoOferta")
    private TipoOferta tipoOferta;

    public void serCanjeado() {
        this.stock--;
        this.canjeado++;
        if (this.stock == 0) {
            this.SinStock = true;
        } else {
            this.SinStock = false;
        }
    }
}