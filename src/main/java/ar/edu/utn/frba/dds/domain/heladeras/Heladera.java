package ar.edu.utn.frba.dds.domain.heladeras;

import ar.edu.utn.frba.dds.domain.heladeras.permisosHeladeras.ReceptorPermisosHeladera;
import ar.edu.utn.frba.dds.domain.heladeras.sensores.movimiento.ReceptorDeMovimiento;
import ar.edu.utn.frba.dds.domain.heladeras.sensores.temperatura.LecturaDeTemperatura;

import ar.edu.utn.frba.dds.domain.heladeras.sensores.temperatura.ReceptorDeTemperatura;
import ar.edu.utn.frba.dds.domain.incidentes.TipoAlerta;
import ar.edu.utn.frba.dds.domain.lugares.Ubicacion;
import ar.edu.utn.frba.dds.domain.suscripciones.*;
import ar.edu.utn.frba.dds.domain.tarjetas.TarjetaDeColaborador;
import ar.edu.utn.frba.dds.domain.viandas.Vianda;
import ar.edu.utn.frba.dds.server.controllers.TecnicoController;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "heladeras")
public class Heladera {

    @Id
    @GeneratedValue
    @Getter
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lugar_id", referencedColumnName = "id")
    private Ubicacion ubicadaEn;

    @Column(name = "nombre")
    @Getter @Setter
    private String nombre;

    @Column(name = "capacidadEnUnidades")
    @Getter @Setter
    private Integer capacidadEnUnidades;

    @Column(name = "fechaAlta", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaAlta;

    /*
    @Column(name = "fechaAlta", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaBaja;
    */

    @Column(name = "fechaColocada", columnDefinition = "DATE")
    private LocalDate fechaColocada;

    @Column(name = "temperaturaMinima")
    private Double temperaturaMinima;

    @Column(name = "temperaturaMaxima")
    private Double temperaturaMaxima;

    @OneToMany(mappedBy = "heladeraDondeEsta", fetch = FetchType.LAZY)
    private List<Vianda> viandasAlmacenadas;

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY)
    private List<Suscripcion> suscripciones;

    /*
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "temperaturaEnGradosCent", column = @Column(name = "ultMedicionTempEnGradosCent")),
            @AttributeOverride(name = "fechaHora", column = @Column(name = "ultMedicionTempFechaHora"))
    })
    private MedicionTemperatura ultimaMedicionTemperatura;
    */

    @Column(name = "estaActiva")
    private Boolean estaActiva = true;

    //private EstadoHeladera estado; cambiar

    @Column(name = "fechaUltimoEstado", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaUltimoEstado;

    @Column(name = "eliminada")
    private Boolean eliminada;

    @Transient
    private SuscriptorBrokerDEPRECATED suscriptorBroker;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "heladera_id") // es bidireccional
    private List<SolicitudApertura> solicitudesAperturas;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "heladera_id")
    private List<AperturaHeladera> aperturasHeladera;

    @Column(name = "cant_viandas")
    private int cantViandas;

    @Transient
    private ReceptorPermisosHeladera receptorPermisosHeladera;

    @Transient
    private ReceptorDeTemperatura receptorDeTemperatura;

    @Transient
    private ReceptorDeMovimiento receptorDeMovimiento;


    public void ingresarVianda(Vianda unaVianda){
        this.getViandasAlmacenadas().add(unaVianda);
    }

    public boolean tieneViandas(){
        return !this.getViandasAlmacenadas().isEmpty();
    }

    public Vianda retirarVianda(){
        return this.getViandasAlmacenadas().get(0);
    }

/*
    public void contadorDeMeses(){
        ScheduledExecutorService equematizadorDeDias = Executors.newSingleThreadScheduledExecutor();
        AtomicInteger contadorDeDias = new AtomicInteger();
        Runnable tarea = () -> {
            contadorDeDias.set(0);
            if(this.getEstado().getClass().getSimpleName().equals("HeladeraActiva"))
                { contadorDeDias.getAndIncrement();}
        };
        equematizadorDeDias.scheduleAtFixedRate(tarea, 0, 5, TimeUnit.DAYS);

        if (contadorDeDias.get() == 30){
            contadorDeDias.set(0);
            mesesActiva += 1;
        }
    }

 */

    public void eliminar(){
        this.setEliminada(true);
    }

    public void darDeAlta(){
        this.setEliminada(false);
    }

    public void desactivar(){
        this.setEstaActiva(false);
    }

    public void activar(){
        this.setEstaActiva(true);
    }

    public void agregarPermisoApertura(SolicitudApertura solicitudApertura){
        this.getSolicitudesAperturas().add(solicitudApertura);
    }

    public void iniciarRecepcionDePermisosApertura(){
       // this.getReceptorPermisosHeladera().start();
    }

    public Boolean tienePermisoPara(TarjetaDeColaborador tarjetaDeColaborador) {
        //return this.getSolicitudesAperturas().stream().anyMatch(solicitud -> solicitud.getCodigoTarjeta().equals(tarjetaDeColaborador.getCodigo()) && solicitud.getFechaHoraVencimiento().isAfter(LocalDateTime.now()));
        return true;
    }

    public void agregarApertura(AperturaHeladera apertura) {
        this.getAperturasHeladera().add(apertura);
    }

    public void notificarBajaStock(){
        this.getSuscripciones().stream()
                .filter(suscripcion -> suscripcion.getClass().equals(PocoStock.class))
                .forEach(suscripcion -> {
                    if(((PocoStock) suscripcion).getViandas_poco_stock() > this.getViandasAlmacenadas().size())
                        ((PocoStock) suscripcion).notificar();
                });
    }

    public void notificarAltoStock(){
        this.getSuscripciones().stream()
            .filter(suscripcion -> suscripcion.getClass().equals(MuchoStock.class))
            .forEach(suscripcion -> {
                if(((MuchoStock) suscripcion).getViandas_mucho_stock() < this.getViandasAlmacenadas().size())
                    ((MuchoStock) suscripcion).notificar();
            });
    }

    public void notificarDesperfecto(TipoAlerta tipoAlerta){
        this.estaActiva = false;
        this.getSuscripciones().stream()
                .filter(suscripcion -> suscripcion.getClass().equals(Desperfecto.class))
                .forEach(Suscripcion::notificar);
        ServiceLocator.instanceOf(TecnicoController.class).reportarAlerta(this, tipoAlerta);
    }

    public void validarTemperatura(LecturaDeTemperatura lectura) {
        if(lectura.getValorEnGrados() < this.getTemperaturaMinima() || lectura.getValorEnGrados() > this.getTemperaturaMaxima())
            this.notificarDesperfecto(TipoAlerta.TEMPERATURA);
    }

    public void recibirFalla(){
        this.estaActiva = false;
    }

    public void serReparada() {
        this.estaActiva = true;
    }

    //Sensor de movimiento -> usar tambien el notificarDesperfecto

}
