package ar.edu.utn.frba.dds.domain.tecnicos;

import ar.edu.utn.frba.dds.domain.calculadores.CalculadorDeDistancias;
import ar.edu.utn.frba.dds.domain.contactos.Contacto;
import ar.edu.utn.frba.dds.domain.contactos.MedioDeContacto;
import ar.edu.utn.frba.dds.domain.documentos.DocumentoDeIdentidad;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.incidentes.Incidente;
import ar.edu.utn.frba.dds.domain.lugares.AreaDeCobertura;
import ar.edu.utn.frba.dds.domain.notificaciones.INotificable;
import ar.edu.utn.frba.dds.domain.sesion.Usuario;
import ar.edu.utn.frba.dds.exceptions.NoPoseeMedioDeContactoException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "tecnicos")
public class Tecnico implements INotificable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Embedded
    private DocumentoDeIdentidad documento;

    @Column(name = "cuil")
    private String cuil;

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "tecnico_id", referencedColumnName = "id")
    private List<Contacto> mediosDeContacto;

    @Embedded
    private AreaDeCobertura areaDeCobertura;

    @Column(name = "eliminado")
    private Boolean eliminado = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "preferenciaContacto")
    private MedioDeContacto preferenciaContacto;

    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "username", referencedColumnName = "username")
    private Usuario usuario;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "tecnico_id", referencedColumnName = "id")
    private List<Incidente> incidentesAsignados = new ArrayList<>();

    public Tecnico(){
        mediosDeContacto = new ArrayList<>();
    }

    public void agregarIncidente(Incidente incidente){
        this.incidentesAsignados.add(incidente);
    }

    public void eliminar(){
        this.setEliminado(true);
    }

    public void reactivar(){
        this.setEliminado(false);
    }

    public void agregarMedioDeContacto(Contacto unMedio){
        this.getMediosDeContacto().add(unMedio);
    }

    public void eliminarMedioDeContacto(Contacto unMedio){
        this.getMediosDeContacto().remove(unMedio);
    }

    @Override
    public Contacto obtenerInfoDeContacto() {
        Contacto medioDeContacto = this.getMediosDeContacto().stream().filter(contacto -> contacto.esDeTipo(this.getPreferenciaContacto())).toList().get(0);
        if(medioDeContacto == null){
            throw new NoPoseeMedioDeContactoException("No posee un medio de contacto registrado para " + this.getPreferenciaContacto().toString());
        } else {
            return medioDeContacto;
        }
    }

    public void repararHeladera(Heladera unaHeladera){
        //TODO
    }

    public void realizarVisita(){
        //TODO
        //VisitaTecnica visitaTecnica = new VisitaTecnica(LocalDateTime.now());
    }

    public boolean esTecnico() {
        return Objects.equals(this.usuario.getRol().getNombre(), "tecnico");
    }

    public Boolean heladeraEstaEnAreaDeCobertura(Heladera unaHeladera){
        CalculadorDeDistancias calculador = new CalculadorDeDistancias();
        double distancia = calculador.calcularDistanciaEntreCoordenadas(this.getAreaDeCobertura().getCoordenada(), unaHeladera.getUbicadaEn().getPuntoGeografico());
        return distancia <= this.getAreaDeCobertura().getRadioCoberturaEnKm();
    }

    /*
    @Override
    public InfoDeContacto obtenerInfoDeContacto() {
        return this.getInfoDeContacto();
    }

    @Override
    public INotificador obtenerNotificador() {
        return this.getPreferenciaNotificador();
    }

    @Override
    public void seleccionarMedioDeNotificacion(MedioDeContacto unMedio) {
        this.setPreferenciaNotificador(NotificadorFactory.createNotificador(unMedio));
    }
    */
}
