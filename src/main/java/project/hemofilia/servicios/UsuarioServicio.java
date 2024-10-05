package project.hemofilia.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.hemofilia.entidades.Usuario;
import project.hemofilia.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public long contarUsuarios() {return usuarioRepositorio.count();}

    public Usuario findByCorreo(String correo) {
        return usuarioRepositorio.findByCorreo(correo).orElse(null);
    }

    public void save(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepositorio.save(usuario);
    }

    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.rol.nombre = 'ROLE_ADMIN'")
    public long contarAdministradores() {
        return 0;
    }
}
