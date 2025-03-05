package ar.edu.utn.frba.dds.utils.cargaMasiva;

import ar.edu.utn.frba.dds.domain.calculadores.CalculadorDeColaboraciones;
import ar.edu.utn.frba.dds.domain.calculadores.CalculadorDePuntos;
import ar.edu.utn.frba.dds.domain.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.domain.colaboradores.PersonaHumana;
import ar.edu.utn.frba.dds.domain.documentos.DocumentoDeIdentidad;
import ar.edu.utn.frba.dds.domain.sesion.Rol;
import ar.edu.utn.frba.dds.domain.sesion.Usuario;
import ar.edu.utn.frba.dds.repositories.CalculadorDePuntosRepository;
import ar.edu.utn.frba.dds.repositories.ColaboracionRepository.ColaboracionesRepository;
import ar.edu.utn.frba.dds.repositories.ColaboradoresRepository;
import ar.edu.utn.frba.dds.repositories.PersonasHumanasRepository;
import ar.edu.utn.frba.dds.repositories.RolRepository;
import ar.edu.utn.frba.dds.server.framework.ServiceLocator;
import ar.edu.utn.frba.dds.utils.HashContrasenia;
import ar.edu.utn.frba.dds.utils.ValidadorContrasenias;
import ar.edu.utn.frba.dds.utils.cargaMasiva.lectoresDeAchivos.ColaboracionLeidaDTO;
import ar.edu.utn.frba.dds.utils.cargaMasiva.lectoresDeAchivos.ILectorArchivo;
import ar.edu.utn.frba.dds.utils.cargaMasiva.lectoresDeAchivos.LectorCSV;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class CargaMasiva {

  @Setter @Getter
  private ILectorArchivo lectorArchivo; //cuando se quiera hacer una carga masiva se le va a poner un lector de archivo, la idea es que cada lector pueda leer distintos tipos de archivos CSV, TSV, etc pero que todos devuelvan algo del tipo List<ColaboracionLeida>
  public CargaMasiva(ILectorArchivo lectorArchivo) {
    this.lectorArchivo = lectorArchivo;
  }

  @SneakyThrows
  public void interpretarColaboracionesLeidas(String pathArchivo){
    //List<PersonaHumana> personasConColaboraciones = ServiceLocator.instanceOf(PersonasHumanasRepository.class).buscarTodos(PersonaHumana.class);
    PersonasHumanasRepository personasConColaboraciones = ServiceLocator.instanceOf(PersonasHumanasRepository.class);

    //System.out.println(personasConColaboraciones);
    List<ColaboracionLeidaDTO> colaboracionLeidas = lectorArchivo.leerArhcivo(pathArchivo);

    for (ColaboracionLeidaDTO unaColaboracionLeida : colaboracionLeidas) {
      PersonaHumana persona = null;

      persona = personasConColaboraciones.findByTipoDocAndDocumento(
              String.valueOf(unaColaboracionLeida.getTipoDoc()),
              unaColaboracionLeida.getDocumento()
      ).orElse(null);

      if (persona == null) {
        // Crear nueva PersonaHumana si no existe en el repositorio
        persona = new PersonaHumana(
                unaColaboracionLeida.getNombre(),
                unaColaboracionLeida.getApellido(),
                LocalDate.now(),
                new DocumentoDeIdentidad(
                        unaColaboracionLeida.getTipoDoc(),
                        unaColaboracionLeida.getDocumento()
                )
        );

        Usuario usuario = new Usuario();
        usuario.setUsername(persona.getNombre() + (int) (Math.random() * 1000));
        usuario.setPassword(usuario.getUsername());

        ServiceLocator.instanceOf(RolRepository.class).buscarPorId(2L, Rol.class).ifPresent(usuario::setRol);
        persona.setUsuario(usuario);

        ServiceLocator.instanceOf(ColaboradoresRepository.class).guardar(persona);
      }

      // Crear y agregar la contribución
      Colaboracion contribucion = ColaboracionFactory.crearInstacia(
              unaColaboracionLeida.getFormaColaboracion(),
              unaColaboracionLeida.getFechaColaboracion().atStartOfDay(),
              unaColaboracionLeida.getCantidad()
      );
      persona.agregarColaboracionRealizada(contribucion);

      //SUMAR PUNTOS
      CalculadorDePuntosRepository repoPuntos = ServiceLocator.instanceOf(CalculadorDePuntosRepository.class);
      CalculadorDePuntos coeficientes = repoPuntos.buscarPorId(1L, CalculadorDePuntos.class)
              .orElseThrow(() -> new IllegalStateException("No se encontraron coeficientes para el cálculo de puntos"));

      Double puntos = CalculadorDePuntos.calcularPuntosCargaMasiva(
              unaColaboracionLeida.getFormaColaboracion(),
              coeficientes,
              unaColaboracionLeida.getCantidad()
      );

      Double puntosDisponibles = Objects.requireNonNullElse(persona.getPuntosDisponibles(), 0.0) + puntos;
      persona.setPuntosDisponibles(puntosDisponibles);
      Double puntosTotales = Objects.requireNonNullElse(persona.getPuntosTotales(), 0.0) + puntos;
      persona.setPuntosTotales(puntosTotales);

      personasConColaboraciones.actualizar(persona);
        /*
        EnviadorMail.enviarMail(
                "<dest>",
                "Alta de Colaborador",
                "Hola \n Queríamos informarle que ha sido dado de alta en el sistema -Grupo 7 DDS desde Java",
                "tpanualdisenogruposiete@gmail.com",
                "<pw>"
        );
        */
    }
  }

  public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
  
    LectorCSV lectorCSV = new LectorCSV();
    CargaMasiva cargaMasiva = new CargaMasiva(lectorCSV);

    cargaMasiva.interpretarColaboracionesLeidas("src/test/java/ar/edu/utn/frba/dds/utilsTests/CSV_PRUEBAS.csv");
  }
}
