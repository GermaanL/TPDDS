package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.domain.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.domain.colaboraciones.FrecuenciaDonacion;
import ar.edu.utn.frba.dds.domain.colaboraciones.colaboracionesGenerales.DonacionDeDinero;
import ar.edu.utn.frba.dds.domain.colaboradores.Colaborador;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.repositories.Daos.EntityManagerDAO;
import ar.edu.utn.frba.dds.repositories.Daos.IDAO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class BaseRepository {

  private IDAO dao;

  public <T> void guardar(T objeto){
    dao.create(objeto);
  }

  public <T> List<T> buscarTodos(Class<T> clase){
    return dao.findAll(clase);
  }

  public <T> Optional<T> buscarPorId(long id, Class<T> clase){
    return dao.find(id, clase);
  }

  public <T> Optional<T> buscarPorId(String id, Class<T> clase){
    return dao.find(id, clase);
  }

  public <T> void actualizar(T objeto){
    dao.update(objeto);
  }

  public <T> void eliminar(T objeto){
    dao.delete(objeto);
  }



  public static void main (String[] args) {
    IDAO dao = new EntityManagerDAO();

    BaseRepository repo = new BaseRepository();
    repo.setDao(dao);

    //Producto producto = new Producto("ProductoNuevo", "imagen1");

//    List<Producto> productos = repo.buscarTodos(Producto.class);

//    for (Producto p : productos) {
//      System.out.println("Producto: " + p.getNombre());
//    }

    Optional<Colaborador>  colaborador= repo.buscarTodos(Colaborador.class).stream().findFirst();

    DonacionDeDinero donacion = new DonacionDeDinero();
    donacion.setMonto(Integer.parseInt("100"));
    donacion.setFrecuencia(FrecuenciaDonacion.MENSUAL);
    donacion.setFechaAlta(LocalDateTime.now());

    donacion.setColaborador(colaborador.get());

    repo.guardar(donacion);

//    repo.actualizar(colaborador.get());
//
//    System.out.println("Colaborador: " + colaborador.get().getDireccion());


//    for (Colaboracion c : colaborador.get().getColaboracionesRealizadas()) {
//      System.out.println("Colaboracion: " + c.getTipo().getNombre());
//    }

   /* Optional<Producto> produ_db = repo.buscarPorId(1l, Producto.class);

    if(produ_db.isPresent()){
      repo.eliminar(produ_db.get());
    }*/
    //repo.guardar(producto);

    /*Optional<? extends Producto> producto_encontrado = repo.buscarPorId(1l ,producto.getClass());

    System.out.println("Producto: " + producto_encontrado.get().getNombre());*/
    //dao.create(producto);
  }

}
