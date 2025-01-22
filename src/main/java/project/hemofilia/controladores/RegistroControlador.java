package project.hemofilia.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    private void crearRolesSiNoExisten() {
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
    public String registrarAdmin(@ModelAttribute("usuario") Usuario usuario) {
        if (usuarioServicio.findByCorreo(usuario.getCorreo()) != null) {
            return "registro";
        }

        Rol rolAdmin = rolServicio.findByNombre("ROLE_ADMIN");
        usuario.setRol(rolAdmin);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioServicio.save(usuario);

        return "redirect:/login";
    }

    @GetMapping("/registrar")
    public String mostrarFormularioRegistrarEmpleado(Model model) {
        long count = usuarioServicio.contarAdministradores();
        if (count == 0) {
            return "redirect:/registro";
        }
        model.addAttribute("usuario", new Usuario());
        return "registrar";
    }

    @PostMapping("/registrar")
    public String registrarEmpleado(@ModelAttribute("usuario") Usuario usuario, Model model, RedirectAttributes redirectAttributes) {
        if (usuarioServicio.findByCorreo(usuario.getCorreo()) != null) {
            model.addAttribute("error", "El correo ya está registrado");
            return "registrar";
        }

        Rol rolEmpleado = rolServicio.findByNombre("ROLE_EMPLEADO");
        usuario.setRol(rolEmpleado);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioServicio.save(usuario);

        redirectAttributes.addFlashAttribute("aviso", "Empleado registrado exitosamente");
        return "redirect:/usuarios/index";
    }
}
