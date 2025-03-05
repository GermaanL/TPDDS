package ar.edu.utn.frba.dds.app;

import ar.edu.utn.frba.dds.domain.contactos.Contacto;
import ar.edu.utn.frba.dds.domain.contactos.MedioDeContacto;
import ar.edu.utn.frba.dds.domain.documentos.DocumentoDeIdentidad;
import ar.edu.utn.frba.dds.domain.documentos.TipoDoc;
import ar.edu.utn.frba.dds.domain.lugares.AreaDeCobertura;
import ar.edu.utn.frba.dds.domain.lugares.Coordenada;
import ar.edu.utn.frba.dds.domain.notificaciones.Notificacion;
import ar.edu.utn.frba.dds.domain.sesion.Rol;
import ar.edu.utn.frba.dds.domain.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.repositories.NotificacionesRepository;
import ar.edu.utn.frba.dds.repositories.RepositorioDeNotificaciones;
import ar.edu.utn.frba.dds.repositories.RepositorioDeTecnicos;
import ar.edu.utn.frba.dds.repositories.RolRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class MainExamplePersistencia implements WithSimplePersistenceUnit {

    private RepositorioDeNotificaciones repositorioDeNotificaciones;
    private RepositorioDeTecnicos repositorioDeTecnicos;
    private NotificacionesRepository repositorioDeNotificaciones2;

    private RolRepository rolRepository;

    public static void main(String[] args) {
        MainExamplePersistencia instance = new MainExamplePersistencia();
        //instance.repositorioDeNotificaciones = new RepositorioDeNotificaciones();
        //instance.repositorioDeTecnicos = new RepositorioDeTecnicos();

        //instance.repositorioDeNotificaciones2 = new NotificacionesRepository();
        //instance.repositorioDeNotificaciones2.setDao(new EntityManagerDAO());
        //instance.guardarNotificaciones2();

        // Prueba 1
        instance.inicializar();

//        instance.guardarRoles();

    }

    private void inicializar(){
        withTransaction(() -> {

        });
    }


    private void guardarRoles() {
        Rol admin = new Rol();
        Rol persona = new Rol();
        Rol personaJuridica = new Rol();
        Rol tecnico = new Rol();

        admin.setNombre("admin");
        persona.setNombre("persona");
        personaJuridica.setNombre("personaJuridica");
        tecnico.setNombre("tecnico");

        //TODO SET ACCIONES

        withTransaction(() -> {
            this.rolRepository.guardar(admin);
            this.rolRepository.guardar(persona);
            this.rolRepository.guardar(personaJuridica);
            this.rolRepository.guardar(tecnico);
        });
    }

    private void actualizarTiposDeNotificaciones() {
        Optional<Notificacion> posibleNotificacion1 = this.repositorioDeNotificaciones.buscarPorId(1L);
        Optional<Notificacion> posibleNotificacion2 = this.repositorioDeNotificaciones.buscarPorId(2L);

        withTransaction(() -> {
            if(posibleNotificacion1.isPresent()) {
                Notificacion notificacion1 = posibleNotificacion1.get();
                notificacion1.setTipo("notif modificada 1");

                this.repositorioDeNotificaciones.modificar(notificacion1);
            }

            if(posibleNotificacion2.isPresent()) {
                Notificacion notificacion2 = posibleNotificacion2.get();
                notificacion2.setTipo("notif modificada 2");

                this.repositorioDeNotificaciones.modificar(notificacion2);
            }
        });
    }

    private void guardarTecnicosYContactos() {
        Tecnico tecnico = new Tecnico();

        DocumentoDeIdentidad documento = new DocumentoDeIdentidad();
        documento.setTipoDocumento(TipoDoc.DNI);
        documento.setNumeroDocumento("1234");

        Contacto contacto1 = new Contacto();
        contacto1.setValor("abc@gmail.com");
        contacto1.setTipo(MedioDeContacto.MAIL);

        Contacto contacto2 = new Contacto();
        contacto2.setValor("12345678");
        contacto2.setTipo(MedioDeContacto.WHATSAPP);

        Coordenada unasCoordenadas = new Coordenada();
        unasCoordenadas.setLatitud(35D);
        unasCoordenadas.setLongitud(40D);

        AreaDeCobertura unArea = new AreaDeCobertura();
        unArea.setCoordenada(unasCoordenadas);
        unArea.setRadioCoberturaEnKm(5D);

        tecnico.setNombre("Juan");
        tecnico.setApellido("Perez");
        tecnico.setDocumento(documento);
        tecnico.setCuil("5678");
        tecnico.agregarMedioDeContacto(contacto1);
        tecnico.agregarMedioDeContacto(contacto2);
        tecnico.setAreaDeCobertura(unArea);

        /*beginTransaction();
        this.repositorioDeServicios.guardar(servicio1);
        this.repositorioDeServicios.guardar(servicio2);
        commitTransaction();*/

        withTransaction(() -> {
            this.repositorioDeTecnicos.guardar(tecnico);
        });
    }
}
