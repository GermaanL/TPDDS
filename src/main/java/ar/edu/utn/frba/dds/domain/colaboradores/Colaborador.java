package ar.edu.utn.frba.dds.domain.colaboradores;

import ar.edu.utn.frba.dds.domain.calculadores.CalculadorDePuntos;
import ar.edu.utn.frba.dds.domain.contactos.Contacto;
import ar.edu.utn.frba.dds.domain.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.domain.colaboraciones.FormaDeColaboracion;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesPersonaJuridica.ofertas.Oferta;
import ar.edu.utn.frba.dds.domain.contactos.MedioDeContacto;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.lugares.Direccion;
import ar.edu.utn.frba.dds.domain.notificaciones.INotificable;
import ar.edu.utn.frba.dds.domain.sesion.Usuario;
import ar.edu.utn.frba.dds.domain.tarjetas.ITitularDeTarjeta;
import ar.edu.utn.frba.dds.domain.tarjetas.Tarjeta;
import ar.edu.utn.frba.dds.exceptions.NoEsColaboracionPosibleException;
import ar.edu.utn.frba.dds.exceptions.NoEsColaboracionRegistradaException;
import ar.edu.utn.frba.dds.exceptions.NoPoseeMedioDeContactoException;
import ar.edu.utn.frba.dds.exceptions.NoTienePuntosSuficientesException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Colaborador implements INotificable /*, ISuscriptorDeHeladeras*/ {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected long id;

  @Embedded
  protected Direccion direccion;

  @ManyToMany
  @JoinTable( name = "colaborador_formaDeColaboracion",
          joinColumns = @JoinColumn(name = "colaborador_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "formaDeColaboracion_id", referencedColumnName = "id"))
  protected List<FormaDeColaboracion> formasDeColaborar;

  @Column(name = "eliminado")
  protected Boolean eliminado = false;

//  @OneToMany
//  @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
  @OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  protected List<Colaboracion> colaboracionesRealizadas = new ArrayList<>();

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
  protected List<Contacto> mediosDeContacto;

  @Enumerated(EnumType.STRING)
  @Column(name = "preferenciaContacto")
  protected MedioDeContacto preferenciaContacto;

  @Column(name = "puntosTotales")
  protected Double puntosTotales;

  @Column(name = "puntosCanjeados")
  protected Double puntosCanjeados;

  @Column(name = "puntosDisponibles")
  protected Double puntosDisponibles;

  @OneToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "username", referencedColumnName = "username")
  protected Usuario usuario;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tarjeta_id", referencedColumnName = "id")
  protected Tarjeta tarjeta;


/*
  @Transient
  private CalculadorDeColaboraciones calculadorDeColaboraciones;

  @Transient
  private PublicadorBroker publicadorBroker;


  @ElementCollection
  @Enumerated(EnumType.STRING)
  private List<TipoSuscripcion> suscriptoA;

  @Column(name = "stock_minimo_suscripcion")
  private Integer stockMinimoSuscripcion;

  @Column(name = "stock_maximo_suscripcion")
  private Integer stockMaximoSuscripcion;
*/

  public void agregarFormaDeColaboracion(FormaDeColaboracion unaForma) {
    this.getFormasDeColaborar().add(unaForma);
  }

  public void eliminarFormaDeColaboracion(FormaDeColaboracion unaForma){
      this.getFormasDeColaborar().remove(unaForma);
  }

  public void agregarMedioDeContacto(Contacto unMedio){
      this.getMediosDeContacto().add(unMedio);
  }

  public void eliminarMedioDeContacto(Contacto unMedio){
      this.getMediosDeContacto().remove(unMedio);
  }

  public void elegirPreferenciaDeContacto(MedioDeContacto unTipo){
    this.setPreferenciaContacto(unTipo);
  }

  public void agregarColaboracionRealizada(Colaboracion unaColaboracion){
      this.colaboracionesRealizadas.add(unaColaboracion);
      unaColaboracion.setColaborador(this);
  }

  public void eliminar(){
      this.setEliminado(true);
  }

  public void reactivar(){
      this.setEliminado(false);
  }

  public void canjearPuntos (Oferta oferta){

      Integer puntosNecesarios = oferta.getPuntosNecesarios();

      if (this.puntosDisponibles - puntosNecesarios < 0 ){
          throw new NoTienePuntosSuficientesException("No tiene puntos suficientes para canjear esta oferta.");
      }
      else {
            this.puntosDisponibles -= puntosNecesarios;
            this.puntosCanjeados += puntosNecesarios;
      }
  }

  public void calcularPuntos(CalculadorDePuntos calculador) {
    this.puntosTotales = calculador.calcularPuntosTotales(this);
    this.puntosDisponibles = this.puntosTotales - this.puntosCanjeados;
  }

  public void realizarColaboracion(Colaboracion unaColaboracion){
//    if (!this.esColaboracionRegistrada(unaColaboracion)) {
//      throw new NoEsColaboracionRegistradaException("Está intentando realizar una colaboración no registrada.");
//    }

//    if(!this.esColaboracionDelTipoCorrecto(unaColaboracion)) {
//      throw new NoEsColaboracionPosibleException("Está intentando realizar una colaboración no permitida para su tipo.");
//    }
    unaColaboracion.realizar();
    this.agregarColaboracionRealizada(unaColaboracion);
  }

  public Boolean esColaboracionRegistrada(Colaboracion unaColaboracion) {
      return this.getFormasDeColaborar().contains(unaColaboracion.getTipo());
  }

  public abstract boolean esColaboracionDelTipoCorrecto(Colaboracion unaColaboracion);

  @Override
  public Contacto obtenerInfoDeContacto() {
    Contacto medioDeContacto = this.getMediosDeContacto().stream().filter(contacto -> contacto.esDeTipo(this.getPreferenciaContacto())).toList().get(0);
    if(medioDeContacto == null){
      throw new NoPoseeMedioDeContactoException("No posee un medio de contacto registrado para " + this.getPreferenciaContacto().toString());
    } else {
      return medioDeContacto;
    }
  }

  public void reportarFallaTecnicaEn(Heladera unaHeladera) {
    //TODO
  }

  public boolean esAdmin() {
    return Objects.equals(this.usuario.getRol().getNombre(), "admin");
  }

  public boolean esTecnico() {
    return Objects.equals(this.usuario.getRol().getNombre(), "tecnico");
  }

  public boolean esPersona() {
    return Objects.equals(this.usuario.getRol().getNombre(), "persona");
  }

  public boolean esPersonaJuridica() {
    return Objects.equals(this.usuario.getRol().getNombre(), "personaJuridica");
  }




  /*
  public Stream<IContribuible> filtrarContribucionesHechasPorNombre (String unNombre){
    return this.getContribucionesHechas().stream().filter(unaContribucion -> unaContribucion.getTipo().equals(unNombre));
  }

  public void solicitarApertura(Heladera unaHeladera){
    SolicitudApertura solicitud = new SolicitudApertura(unaHeladera.getId(), this.getTarjetaDeColaborador().getCodigo());
    // persistir la solicitud con repositorio en algún medio

    SolicitadoraDePermisosHeladera.getInstance().solicitarApertura(solicitud);
  }

  public void reportarFallaTecnica(FallaTecnica unaFalla){
    this.fallasReportadas.add(unaFalla);
  }

  @Override
  public Boolean correspondeNotificarPocoStockDe(Heladera unaHeladera){
    Boolean estaSuscripto = this.getSuscriptoA().contains(TipoSuscripcion.HELADERA_POCO_STOCK);
    Integer stockHeladera = unaHeladera.getStock();
    Integer stockSeteado = this.getStockMinimoSuscripcion();
    return estaSuscripto && (stockHeladera == stockSeteado);
  }

  @Override
  public Boolean correspondeNotificarMuchoStockDe(Heladera unaHeladera){
    Boolean estaSuscripto = this.getSuscriptoA().contains(TipoSuscripcion.HELADERA_LLENANDOSE);
    Integer faltaParaLlenar = unaHeladera.getCapacidad() - unaHeladera.getStock();
    Integer stockSeteado = this.getStockMaximoSuscripcion();
    return estaSuscripto && (faltaParaLlenar == stockSeteado);
  }

  @Override
  public Boolean correspondeNotificarInactividadDe(Heladera unaHeladera){
    Boolean estaSuscripto = this.getSuscriptoA().contains(TipoSuscripcion.HELADERA_INACTIVA);
    return estaSuscripto;
  }
  */

}
