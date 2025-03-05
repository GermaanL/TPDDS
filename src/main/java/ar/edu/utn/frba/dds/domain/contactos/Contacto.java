package ar.edu.utn.frba.dds.domain.contactos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contactos")
public class Contacto {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "valor")
    private String valor;

    @Enumerated(EnumType.STRING)
    private MedioDeContacto tipo;

    public Boolean esDeTipo(MedioDeContacto unTipo){
        return this.getTipo().equals(unTipo);
    }

}
