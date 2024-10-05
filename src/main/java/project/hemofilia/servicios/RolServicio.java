package project.hemofilia.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.hemofilia.entidades.Rol;
import project.hemofilia.repositorios.RolRepositorio;

@Service
public class RolServicio {

    @Autowired
    private RolRepositorio rolRepositorio;

    public boolean existePorNombre(String nombreRol) {
        return rolRepositorio.existsByNombre(nombreRol);
    }

    public void save(Rol rol) {
        rolRepositorio.save(rol);
    }

    public Rol findByNombre(String nombre) {
        return rolRepositorio.findByNombre(nombre);
    }
}
