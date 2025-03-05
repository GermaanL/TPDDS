package ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaHumana;

import ar.edu.utn.frba.dds.domain.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.exceptions.HeladeraDestinoSinCapacidadException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "distribuciones_de_viandas")
public class DistribucionDeVianda extends Colaboracion {

    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "heladeraOrigen_id", referencedColumnName = "id")
    private Heladera heladeraOrigen;

    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "heladeraDestino_id", referencedColumnName = "id")
    private Heladera heladeraDestino;

    @Column(name = "cantidadDeViandasAMover")
    private Integer cantidadDeViandasAMover;

    @Column(name = "motivo")
    private String movartivo;

    @Column(name = "fechaDeDistribucion", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaDeDistribucion;

    public Boolean hayCapacidadEnDestino(){
        //Integer cantidad_en_destino = this.getHeladeraDestino().getCantidadDeViandas();
        //Integer capacidad_en_destino = this.getHeladeraDestino().getCapacidad();
        //Integer capacidad_disponible = capacidad_en_destino - cantidad_en_destino;
        //return this.getCantidadDeViandasAMover() <= capacidad_disponible;
        return true;
    }

    @Override
    public void realizar() {
        if (!hayCapacidadEnDestino())
            throw new HeladeraDestinoSinCapacidadException("La heladera destino no tiene capacidad suficiente para mover la cantidad de viandas deseada.");

        //TODO
        //Continuar el método
    }

    /*
    private void enviarViandasADestino(){
        if (this.cantidadDeViandasAMover > this.heladeraOrigen.getCantidadDeViandas()){
            throw new LaHeladeraOrigenNoPoseeLaCantidadDeViandasAMoverException("La heladera de origen no posee una cantidad suficiente de viandas para realizar el movimiento deseado. La heladera origen posee "+ heladeraOrigen.getCantidadDeViandas()+" vianda");
        }
        else{
            for (int i = 0; i < this.cantidadDeViandasAMover; i++){
                this.moverUnaVianda(heladeraOrigen, heladeraDestino);
            }
        }
    }

    private void moverUnaVianda(Heladera heladeraOrigen, Heladera heladeraDestino){
        tarjetaDeColaborador.abrir(heladeraOrigen);
        Vianda viandaOrigen = heladeraOrigen.obtenerVianda();
        tarjetaDeColaborador.abrir(heladeraDestino);
        heladeraDestino.ingresarVianda(viandaOrigen);
        heladeraOrigen.quitarVianda();
    }

    public void realizarContribucion(){
        this.enviarViandasADestino();
    }
    */

}
