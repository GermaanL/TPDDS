package ar.edu.utn.frba.dds.repositories.DEPRECATED;

import ar.edu.utn.frba.dds.domain.heladeras.SolicitudApertura;

import java.util.List;

public interface ISolicitudesAperturaDAO {

    public void save(SolicitudApertura unaHeladera);
    public SolicitudApertura find(String unId);
    public List<SolicitudApertura> findAll();
    List<SolicitudApertura>  findAllByHeladeraId(String unId);
    List<SolicitudApertura>  findAllByTarjetaId(String unId);
}
