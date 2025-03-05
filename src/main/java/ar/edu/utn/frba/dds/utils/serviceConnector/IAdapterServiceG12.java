package ar.edu.utn.frba.dds.utils.serviceConnector;

import java.util.List;

public interface IAdapterServiceG12 {
    public void postInformacion(ServiceG12DTO serviceG12DTO);
    public List<RespuestaServiceDTO> getInformacion();
}
