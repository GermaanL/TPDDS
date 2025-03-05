package ar.edu.utn.frba.dds.domain.calculadores;

import ar.edu.utn.frba.dds.domain.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesGenerales.DonacionDeDinero;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaHumana.DistribucionDeVianda;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaHumana.DonacionDeVianda;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaHumana.RegistroPersonaVulnerable;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaJuridica.ColocacionHeladera;

import java.util.List;
import java.util.stream.Stream;

public class CalculadorDeColaboraciones {

    public Stream<Colaboracion> filtrarColaboracionesHechasPorTipo (Colaborador unColaborador, String unTipo) {
        return unColaborador.getColaboracionesRealizadas().stream().filter(unaColaboracion -> unaColaboracion.getTipo().getNombre().equals(unTipo));
    }

    public List<DonacionDeDinero> donacionesDeDineroHechas(Colaborador unColaborador) {
        return unColaborador.getColaboracionesRealizadas().stream()
                .filter(unaColaboracion -> unaColaboracion instanceof DonacionDeDinero)
                .map(unaColaboracion -> (DonacionDeDinero) unaColaboracion)
                .toList();
    }

    public List<DistribucionDeVianda> distribucionesDeViandasHechas(Colaborador unColaborador){
        return unColaborador.getColaboracionesRealizadas().stream()
                .filter(unaColaboracion -> unaColaboracion instanceof DistribucionDeVianda)
                .map(unaColaboracion -> (DistribucionDeVianda) unaColaboracion)
                .toList();
    }

    public List<DonacionDeVianda> donacionesDeViandasHechas(Colaborador unColaborador){
        return unColaborador.getColaboracionesRealizadas().stream()
                .filter(unaColaboracion -> unaColaboracion instanceof DonacionDeVianda)
                .map(unaColaboracion -> (DonacionDeVianda) unaColaboracion)
                .toList();
    }

    public List<ColocacionHeladera> colocacionesDeHeladerasHechas(Colaborador unColaborador){
        return unColaborador.getColaboracionesRealizadas().stream()
                .filter(unaColaboracion -> unaColaboracion instanceof ColocacionHeladera)
                .map(unaColaboracion -> (ColocacionHeladera) unaColaboracion)
                .toList();
    }

    public List<RegistroPersonaVulnerable> registrosDePersonaVulnerablesHechos(Colaborador unColaborador){
        return unColaborador.getColaboracionesRealizadas().stream()
                .filter(unaColaboracion -> unaColaboracion instanceof RegistroPersonaVulnerable)
                .map(unaColaboracion -> (RegistroPersonaVulnerable) unaColaboracion)
                .toList();
    }

    public Double pesosDonados(Colaborador unColaborador){
        return this.donacionesDeDineroHechas(unColaborador).stream()
                .mapToDouble(DonacionDeDinero::getMonto)
                .sum();
    }

    public Integer viandasDistribuidas(Colaborador unColaborador){
        return this.distribucionesDeViandasHechas(unColaborador).stream()
                .mapToInt(DistribucionDeVianda::getCantidadDeViandasAMover)
                .sum();
    }

    public Integer viandasDonadas(Colaborador unColaborador){
        return this.donacionesDeViandasHechas(unColaborador).stream()
                .mapToInt(DonacionDeVianda::getCantidad)
                .sum();
    }


    public Integer heladerasColocadasActivas(Colaborador unColaborador){
        /*return this.colocacionesDeHeladerasHechas(unColaborador).stream()
                .filter(unaColocacion -> unaColocacion.getHeladeraColocada().getEstaActiva() instanceof HeladeraActiva)
                .toList()
                .size();

         */
        return 0;
    }

    public Integer tarjetasRepartidas(Colaborador unColaborador){
        return this.registrosDePersonaVulnerablesHechos(unColaborador).size();
    }
}
