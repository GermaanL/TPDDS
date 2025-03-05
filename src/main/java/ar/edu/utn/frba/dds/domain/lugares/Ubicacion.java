package ar.edu.utn.frba.dds.domain.lugares;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "lugares")
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Embedded
    private Coordenada puntoGeografico;

    @Embedded
    private Direccion direccion;
}
