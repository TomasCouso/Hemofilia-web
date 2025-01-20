package project.hemofilia.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.hemofilia.entidades.Usuario;
import project.hemofilia.repositorios.UsuarioRepositorio;

import java.util.List;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public long contarUsuarios() {
        return usuarioRepositorio.count();
    }

    public Usuario findByCorreo(String correo) {
        return usuarioRepositorio.findByCorreo(correo).orElse(null);
    }

    public void save(Usuario usuario) {
        usuarioRepositorio.save(usuario);
    }

    public void eliminarUsuario (Usuario usuario) {
        usuarioRepositorio.deleteByCorreo(usuario.getCorreo());
    }

    public List<Usuario> findAll() {return usuarioRepositorio.findAll();}

    public Usuario findBynombre(String nombre) { return usuarioRepositorio.findByNombre(nombre).orElse(null); }

    public long contarAdministradores() {
        return usuarioRepositorio.contarAdministradores();
    }
}
