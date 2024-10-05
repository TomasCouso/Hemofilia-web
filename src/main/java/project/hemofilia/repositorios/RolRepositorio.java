package project.hemofilia.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hemofilia.entidades.Rol;


public interface RolRepositorio extends JpaRepository<Rol, Long> {

    Rol findByNombre(String nombre);

    boolean existsByNombre(String nombreRol);
}
