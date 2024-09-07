package project.hemofilia.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hemofilia.entidades.Usuario;

import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
}
