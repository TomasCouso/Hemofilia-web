package project.hemofilia.entidades;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

//Inserción de datos de ejemplo en la tabla Rol
//INSERT INTO rol (nombre) VALUES ('ADMIN');
//INSERT INTO rol (nombre) VALUES ('USER');

@Data
@NoArgsConstructor
@Entity
@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;
}
