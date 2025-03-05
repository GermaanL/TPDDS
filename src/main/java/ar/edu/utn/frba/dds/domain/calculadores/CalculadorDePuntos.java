package ar.edu.utn.frba.dds.domain.calculadores;

import ar.edu.utn.frba.dds.domain.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.domain.colaboraciones.FrecuenciaDonacion;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesGenerales.DonacionDeDinero;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaHumana.DistribucionDeVianda;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaHumana.DonacionDeVianda;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaHumana.RegistroPersonaVulnerable;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaJuridica.ColocacionHeladera;
import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.personasVulnerables.PersonaVulnerable;
import ar.edu.utn.frba.dds.domain.viandas.Vianda;
import ar.edu.utn.frba.dds.repositories.CalculadorDePuntosRepository;
import ar.edu.utn.frba.dds.repositories.ColaboradoresRepository;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "calculadordepuntos")
public class CalculadorDePuntos {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "coeficiente_pesos_donados")
    private Double coeficientePesosDonados;

    @Column(name = "coeficiente_viandas_distribuidas")
    private Double coeficienteViandasDistribuidas;

    @Column(name = "coeficiente_viandas_donadas")
    private Double coeficienteViandasDonadas;

    @Column(name = "coeficiente_tarjetas_repartidas")
    private Double coeficienteTarjetasRepartidas;

    @Column(name = "coeficiente_heladeras_activas")
    private Double coeficienteHeladerasActivas;

    @Column(name = "fechaAlta", columnDefinition = "DATE")
    private LocalDate fechaAlta; //????


    public static Double calcularPuntosTotalesDe(CalculadorDePuntosRepository repoPuntos, Colaborador colaborador) {
        // Obtiene los coeficientes de puntos desde la base de datos
        CalculadorDePuntos coeficientes = repoPuntos.buscarPorId(1L, CalculadorDePuntos.class).orElse(null);
        CalculadorDeColaboraciones calculadorDeColaboraciones = new CalculadorDeColaboraciones();

        if (coeficientes == null) {
            return 0.0; // Manejo de error
        }

        // Calcula los puntos utilizando las métricas del colaborador
        double puntosTotales =
                coeficientes.getCoeficientePesosDonados() * calculadorDeColaboraciones.pesosDonados(colaborador)
                        + coeficientes.getCoeficienteViandasDistribuidas() * calculadorDeColaboraciones.viandasDistribuidas(colaborador)
                        + coeficientes.getCoeficienteViandasDonadas() * calculadorDeColaboraciones.viandasDonadas(colaborador)
                        + coeficientes.getCoeficienteHeladerasActivas() * calculadorDeColaboraciones.heladerasColocadasActivas(colaborador)
                        + coeficientes.getCoeficienteTarjetasRepartidas() * calculadorDeColaboraciones.tarjetasRepartidas(colaborador);

        return puntosTotales;
    }

    public Double calcularPuntosTotales(Colaborador colaborador) {
        // Obtiene los coeficientes de puntos desde la base de datos
        CalculadorDeColaboraciones calculadorDeColaboraciones = new CalculadorDeColaboraciones();

        System.out.print("Coeficientes = " + this.getCoeficientePesosDonados() + " , " +this.getCoeficienteViandasDonadas() );
        // Calcula los puntos utilizando las métricas del colaborador
        double puntosTotales =
                this.getCoeficientePesosDonados() * calculadorDeColaboraciones.pesosDonados(colaborador)
                        + this.getCoeficienteViandasDistribuidas() * calculadorDeColaboraciones.viandasDistribuidas(colaborador)
                        + this.getCoeficienteViandasDonadas() * calculadorDeColaboraciones.viandasDonadas(colaborador)
                        + this.getCoeficienteHeladerasActivas() * calculadorDeColaboraciones.heladerasColocadasActivas(colaborador)
                        + this.getCoeficienteTarjetasRepartidas() * calculadorDeColaboraciones.tarjetasRepartidas(colaborador);
        return puntosTotales;
    }


    //TODO REVISAR CARGA MASIVA
    public static double calcularPuntosCargaMasiva(String formaColaboracion, CalculadorDePuntos coeficientes, double cantidad) {
        return switch (formaColaboracion) {
            case "DINERO" -> coeficientes.getCoeficientePesosDonados() * cantidad;
            case "DONACION_VIANDAS" -> coeficientes.getCoeficienteViandasDonadas() * cantidad;
            case "REDISTRIBUCION_VIANDAS" -> coeficientes.getCoeficienteViandasDistribuidas() * cantidad;
            case "ENTREGA_TARJETAS" -> coeficientes.getCoeficienteTarjetasRepartidas() * cantidad;
            default -> throw new IllegalArgumentException("Tipo de colaboración desconocido: " + formaColaboracion);
        };
    }

    public Integer sumatoriaDeMesesActiva (Colaborador unColaborador, CalculadorDeColaboraciones unCalculadorDeColaboraciones){
        //return unCalculadorDeColaboraciones.colocacionesDeHeladerasHechas(unColaborador).stream().mapToInt(x->x.getHeladeraColocada().getMesesActiva()).sum();
        return 0; //TODO
    }

    public Double calcularPuntosPorColaboracion(Colaboracion colaboracion) {
        if (colaboracion instanceof DonacionDeDinero) {
            return this.coeficientePesosDonados * ((DonacionDeDinero) colaboracion).getMonto();
        } else if (colaboracion instanceof DistribucionDeVianda) {
            return this.coeficienteViandasDistribuidas * ((DistribucionDeVianda) colaboracion).getCantidadDeViandasAMover();
        } else if (colaboracion instanceof DonacionDeVianda) {
            return coeficienteViandasDonadas * ((DonacionDeVianda) colaboracion).getCantidad();
        } else if (colaboracion instanceof ColocacionHeladera) {
            //[CANTIDAD_HELADERAS_ACTIVAS] * [∑ MESES_ACTIVAS] * 5 ??! //TODO - AGREGAR FECHA BAJA - CHEQUEO POR TIEMPO?
            return ((ColocacionHeladera) colaboracion).getHeladeraColocada().getEstaActiva() ? coeficienteHeladerasActivas : 0;
        } else if (colaboracion instanceof RegistroPersonaVulnerable) {

            return coeficienteTarjetasRepartidas;
        }
        return 0.0;
    }

    /*
    public Double calcularPuntosDisponiblesDe(Colaborador unColaborador, CalculadorDeColaboraciones unCalculadorDeColaboraciones){
        return this.calcularPuntosTotalesDe(unColaborador, unCalculadorDeColaboraciones) - unColaborador.getPuntosCanjeados() ;
    }

     */

    public static void main(String[] args) {
        CalculadorDePuntos calculador = ServiceLocator.instanceOf(CalculadorDePuntosRepository.class).buscarPorId(1L,CalculadorDePuntos.class).get();

        Colaborador colaborador = ServiceLocator.instanceOf(ColaboradoresRepository.class).buscarPorUsername("admin").get();

        Double puntos = calculador.calcularPuntosTotales(colaborador);
        System.out.print("PUNTOS TOTALES = "+ puntos + "\n");
    }
}
