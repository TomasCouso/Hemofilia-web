package project.hemofilia.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.hemofilia.entidades.Rol;
import project.hemofilia.entidades.Usuario;
import project.hemofilia.servicios.RolServicio;
import project.hemofilia.servicios.UsuarioServicio;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping
public class RegistroControlador {

    @Autowired
    private RolServicio rolServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    private void crearRolesSiNoExisten() {
        // Lista de roles que necesitas
        List<String> roles = Arrays.asList("ROLE_ADMIN", "ROLE_EMPLEADO");

        for (String nombreRol : roles) {
            if (!rolServicio.existePorNombre(nombreRol)) {
                Rol rol = new Rol();
                rol.setNombre(nombreRol);
                rolServicio.save(rol);
            }
        }
    }

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        long count = usuarioServicio.contarAdministradores();
        if (count > 0) {
            return "redirect:/login";
        }
        crearRolesSiNoExisten();
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@RequestParam("correo") String correo,
                                   @RequestParam("nombre") String nombre,
                                   @RequestParam("password") String password,
                                   Model model) {

        if (usuarioServicio.findByCorreo(correo) != null) {
            model.addAttribute("error", "El correo ya está registrado");
            return "redirect:/registro";
        }

        // Crear el nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setNombre(nombre);
        usuario.setPassword(password);

        long count = usuarioServicio.contarAdministradores();
        Rol usuarioRol = null;
        if (count > 0) {
            usuarioRol = rolServicio.findByNombre("ROLE_ADMIN");
        }else{
            usuarioRol = rolServicio.findByNombre("ROLE_EMPLEADO");
        }
        usuario.setRol(usuarioRol);

        // Guardar el usuario en la base de datos
        usuarioServicio.save(usuario);

        if (usuarioRol.getNombre().equals("ROLE_ADMIN")) {
            return "redirect:/login";
        }else {
            return "redirect:/empleado/historiaClinica";
        }
    }
}
