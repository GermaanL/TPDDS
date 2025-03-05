package ar.edu.utn.frba.dds.server.framework;


import ar.edu.utn.frba.dds.repositories.*;
import ar.edu.utn.frba.dds.repositories.ColaboracionRepository.ColaboracionesRepository;
import ar.edu.utn.frba.dds.repositories.Daos.EntityManagerDAO;
import ar.edu.utn.frba.dds.repositories.Daos.IDAO;
import ar.edu.utn.frba.dds.server.controllers.*;
import ar.edu.utn.frba.dds.server.obervabilidad.DDMetricsUtils;
import ar.edu.utn.frba.dds.server.obervabilidad.DataDogSingleton;
import ar.edu.utn.frba.dds.utils.serviceConnector.AdapterServiceG12;
import ar.edu.utn.frba.dds.utils.serviceConnector.ServiceG12Connector;
import io.javalin.micrometer.MicrometerPlugin;
import io.micrometer.core.instrument.step.StepMeterRegistry;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
    private static Map<String, Object> instances = new HashMap<>();

    public static <T> T instanceOf(Class<T> componentClass) {
        String componentName = componentClass.getName();

        if (!instances.containsKey(componentName)) {
            if(componentName.equals(StepMeterRegistry.class.getName())) {
                StepMeterRegistry instance = DataDogSingleton.getRegistry();
                instances.put(componentName, instance);
            } else if(componentName.equals(MicrometerPlugin.class.getName())) {
                MicrometerPlugin instance = DataDogSingleton.getMicrometerPlugin();
                instances.put(componentName, instance);
            } else if(componentName.equals(DDMetricsUtils.class.getName())) {
                DDMetricsUtils instance = DataDogSingleton.getMetricsUtils();
                instances.put(componentName, instance);
            } else if(componentName.equals(HeladeraController.class.getName())) {
                HeladeraController instance = new HeladeraController(
                        instanceOf(HeladerasRepository.class),
                        instanceOf(ColaboradoresRepository.class));
                instances.put(componentName, instance);
            }
            else if (componentName.equals(HeladerasRepository.class.getName())) {
                HeladerasRepository instance = new HeladerasRepository();
                IDAO dao = new EntityManagerDAO();
                instance.setDao(dao);
                instances.put(componentName, instance);
            }
            else if (componentName.equals(SuscripcionesRepository.class.getName())) {
                SuscripcionesRepository instance = new SuscripcionesRepository();
                IDAO dao = new EntityManagerDAO();
                instance.setDao(dao);
                instances.put(componentName, instance);
            }
            else if (componentName.equals(UbicacionesRepository.class.getName())) {
                UbicacionesRepository instance = new UbicacionesRepository();
                IDAO dao = new EntityManagerDAO();
                instance.setDao(dao);
                instances.put(componentName, instance);
            }
            else if (componentName.equals(LecturaDeTemperaturaRepository.class.getName())) {
                LecturaDeTemperaturaRepository instance = new LecturaDeTemperaturaRepository();
                IDAO dao = new EntityManagerDAO();
                instance.setDao(dao);
                instances.put(componentName, instance);
            }
            else if(componentName.equals(ColaboracionController.class.getName())) {
                ColaboracionController instance = new ColaboracionController(
                        instanceOf(ColaboracionesRepository.class),
                        instanceOf(ColaboradoresRepository.class),
                        instanceOf(CalculadorDePuntosRepository.class),
                        instanceOf(HeladerasRepository.class),
                        instanceOf(FormaDeColaboracionRepository.class));
                instances.put(componentName, instance);
            }
            else if (componentName.equals(ColaboracionesRepository.class.getName())) {
                ColaboracionesRepository instance = new ColaboracionesRepository();
                IDAO dao = new EntityManagerDAO();
                instance.setDao(dao);
                instances.put(componentName, instance);
            }
            //Agregar colaboradores controller aca
            else if(componentName.equals(ColaboradoresRepository.class.getName())){
                ColaboradoresRepository instance = new ColaboradoresRepository();
                IDAO dao = new EntityManagerDAO();
                instance.setDao(dao);
                instances.put(componentName, instance);
            }
            else if(componentName.equals(PersonaVulnerableController.class.getName())) {
                PersonaVulnerableController instance = new PersonaVulnerableController(instanceOf(PersonaVulnerableRepository.class));
                instances.put(componentName, instance);
            }
            else if (componentName.equals(PersonaVulnerableRepository.class.getName())) {
                PersonaVulnerableRepository instance = new PersonaVulnerableRepository();
                IDAO dao = new EntityManagerDAO();
                instance.setDao(dao);
                instances.put(componentName, instance);
            }
            else if(componentName.equals(SesionController.class.getName())) {
                SesionController instance = new SesionController(instanceOf(SesionRepository.class));
                instances.put(componentName, instance);
            }
            else if (componentName.equals(SesionRepository.class.getName())) {
                SesionRepository instance = new SesionRepository();
                IDAO dao = new EntityManagerDAO();
                instance.setDao(dao);
                instances.put(componentName, instance);
            }
            else if(componentName.equals(OfertasRepository.class.getName())) {
                OfertasRepository instance = new OfertasRepository();
                IDAO dao = new EntityManagerDAO();  // Configura el DAO
                instance.setDao(dao);               // Asigna el DAO al repositorio
                instances.put(componentName, instance);
            }
            else if(componentName.equals(OfertasController.class.getName())) {
                OfertasController instance = new OfertasController(
                        instanceOf(OfertasRepository.class),
                        instanceOf(ColaboradoresRepository.class)
                );
                instances.put(componentName, instance);
            }
            else if(componentName.equals(FormaDeColaboracionRepository.class.getName())) {
                FormaDeColaboracionRepository instance = new FormaDeColaboracionRepository();
                IDAO dao = new EntityManagerDAO();  // Configura el DAO
                instance.setDao(dao);               // Asigna el DAO al repositorio
                instances.put(componentName, instance);
            }
            else if(componentName.equals(AdminController.class.getName())) {
                AdminController instance = new AdminController(
                        instanceOf(HeladerasRepository.class),
                        instanceOf(ColaboradoresRepository.class),
                        instanceOf(TarjetasRepository.class)
                );
                instances.put(componentName, instance);
            }
            else if (componentName.equals(AlertaRepository.class.getName())) {
                AlertaRepository instance = new AlertaRepository();
                IDAO dao = new EntityManagerDAO();
                instance.setDao(dao);
                instances.put(componentName, instance);
            }
            else if(componentName.equals(ReporteFallaController.class.getName())) {
                ReporteFallaController instance = new ReporteFallaController(
                        instanceOf(ReporteFallaRepository.class),
                        instanceOf(HeladerasRepository.class),
                        instanceOf(AlertaRepository.class)
                );
                instances.put(componentName, instance);
            }
            else if (componentName.equals(ReporteFallaRepository.class.getName())) {
                ReporteFallaRepository instance = new ReporteFallaRepository();
                IDAO dao = new EntityManagerDAO();
                instance.setDao(dao);
                instances.put(componentName, instance);
            }
            else if (componentName.equals(ViandaRepository.class.getName())) {
                ViandaRepository instance = new ViandaRepository();
                IDAO dao = new EntityManagerDAO();
                instance.setDao(dao);
                instances.put(componentName, instance);
            }
            else if (componentName.equals(TecnicosRepository.class.getName())) {
                TecnicosRepository instance = new TecnicosRepository();
                IDAO dao = new EntityManagerDAO();
                instance.setDao(dao);
                instances.put(componentName, instance);
            }
            else if(componentName.equals(TecnicoController.class.getName())) {
                TecnicoController instance = new TecnicoController(
                        instanceOf(TecnicosRepository.class),
                        instanceOf(HeladerasRepository.class)
                );
                instances.put(componentName, instance);
            }
            else if (componentName.equals(ContactosRepository.class.getName())) {
                ContactosRepository instance = new ContactosRepository();
                IDAO dao = new EntityManagerDAO();
                instance.setDao(dao);
                instances.put(componentName, instance);
            }
            else if (componentName.equals(CalculadorDePuntosRepository.class.getName())) {
                CalculadorDePuntosRepository instance = new CalculadorDePuntosRepository();
                IDAO dao = new EntityManagerDAO();
                instance.setDao(dao);
                instances.put(componentName, instance);
            }
            else if (componentName.equals(RolRepository.class.getName())) {
                RolRepository instance = new RolRepository();
                IDAO dao = new EntityManagerDAO();
                instance.setDao(dao);
                instances.put(componentName, instance);
            }
            else if (componentName.equals(PersonasHumanasRepository.class.getName())) {
                PersonasHumanasRepository instance = new PersonasHumanasRepository();
                IDAO dao = new EntityManagerDAO();
                instance.setDao(dao);
                instances.put(componentName, instance);
            }
            else if (componentName.equals(TarjetasRepository.class.getName())) {
                TarjetasRepository instance = new TarjetasRepository();
                IDAO dao = new EntityManagerDAO();
                instance.setDao(dao);
                instances.put(componentName, instance);
            }
            else if (componentName.equals(VisitasTecnicasRepository.class.getName())) {
                VisitasTecnicasRepository instance = new VisitasTecnicasRepository();
                IDAO dao = new EntityManagerDAO();
                instance.setDao(dao);
                instances.put(componentName, instance);
            }
            else if (componentName.equals(AccionesRepository.class.getName())) {
                AccionesRepository instance = new AccionesRepository();
                IDAO dao = new EntityManagerDAO();
                instance.setDao(dao);
                instances.put(componentName, instance);
            }
            else if (componentName.equals(IncidentesRepository.class.getName())) {
                IncidentesRepository instance = new IncidentesRepository();
                IDAO dao = new EntityManagerDAO();
                instance.setDao(dao);
                instances.put(componentName, instance);
            }
            else if (componentName.equals(SolicitudesDeAperturaRepository.class.getName())){
                SolicitudesDeAperturaRepository instance = new SolicitudesDeAperturaRepository();
                IDAO dao = new EntityManagerDAO();
                instance.setDao(dao);
                instances.put(componentName, instance);
            }
            else if (componentName.equals(AdapterServiceG12.class.getName())){
                AdapterServiceG12 instance = new AdapterServiceG12(new ServiceG12Connector());
                instances.put(componentName, instance);
            }
            else if(componentName.equals(TarjetaController.class.getName())) {
                TarjetaController instance = new TarjetaController(
                    instanceOf(TarjetasRepository.class)
                );

                instances.put(componentName, instance);
            }  else if (componentName.equals(ReparacionesController.class.getName())) {
                ReparacionesController instance = new ReparacionesController(
                        instanceOf(VisitasTecnicasRepository.class),
                        instanceOf(TecnicosRepository.class),
                        instanceOf(IncidentesRepository.class)
                );
                instances.put(componentName, instance);
            }

        }

        return (T) instances.get(componentName);
    }
}
