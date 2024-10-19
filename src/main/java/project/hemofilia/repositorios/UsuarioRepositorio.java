package project.hemofilia.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.hemofilia.entidades.Usuario;

import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);

    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.rol.nombre = 'ROLE_ADMIN'")
    long contarAdministradores();
}
