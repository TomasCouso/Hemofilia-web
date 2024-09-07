package project.hemofilia.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Inserción de datos de ejemplo en la tabla Usuario
//INSERT INTO usuario (nombre, correo, password, id_rol) VALUES
//('Juan Pérez', 'juan@example.com', 'password123', 1),
// ('Ana Gómez', 'ana@example.com', 'password456', 2);


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "correo", nullable = false, unique = true)
    private String correo;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne
    @JoinColumn(name = "id_rol", referencedColumnName = "id", nullable = false)
    private Rol rol;
}
