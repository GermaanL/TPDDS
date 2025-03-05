package ar.edu.utn.frba.dds.utils.cargaMasiva.lectoresDeAchivos;

import java.util.List;

public interface ILectorArchivo {

   List<ColaboracionLeidaDTO> leerArhcivo(String pathArchivo);
}
