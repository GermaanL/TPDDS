package ar.edu.utn.frba.dds.server.obervabilidad;

import io.javalin.micrometer.MicrometerPlugin;
import io.micrometer.core.instrument.step.StepMeterRegistry;

public class DataDogSingleton {

    // Singleton - Variables compartidas
    private static volatile StepMeterRegistry instanceRegistry = null;
    private static volatile MicrometerPlugin micrometerPlugin = null;
    private static volatile DDMetricsUtils metricsUtils = null;


    // Constructor privado
    private DataDogSingleton() {
        DDMetricsUtils utils = new DDMetricsUtils("transferencias");
        StepMeterRegistry registry = utils.getRegistry();
        MicrometerPlugin plugin = new MicrometerPlugin(configure -> configure.registry = registry);
        metricsUtils = utils;
        instanceRegistry = registry;
        micrometerPlugin = plugin;
    }

    public static StepMeterRegistry getRegistry() {
        if (instanceRegistry == null) {
            synchronized (DataDogSingleton.class) {
                if (instanceRegistry == null) {
                    new DataDogSingleton();
                }
            }
        }
        return instanceRegistry;
    }

    public static MicrometerPlugin getMicrometerPlugin() {
        if (micrometerPlugin == null) {
            synchronized (DataDogSingleton.class) {
                if (micrometerPlugin == null) {
                    new DataDogSingleton();
                }
            }
        }
        return micrometerPlugin;
    }

    public static DDMetricsUtils getMetricsUtils() {
        if (metricsUtils == null) {
            synchronized (DataDogSingleton.class) {
                if (metricsUtils == null) {
                    new DataDogSingleton();
                }
            }
        }
        return metricsUtils;
    }
}

