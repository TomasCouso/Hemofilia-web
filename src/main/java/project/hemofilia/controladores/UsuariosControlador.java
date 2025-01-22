package project.hemofilia.controladores;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.hemofilia.entidades.Rol;
import project.hemofilia.entidades.Usuario;
import project.hemofilia.servicios.RolServicio;
import project.hemofilia.servicios.UsuarioServicio;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuariosControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private RolServicio rolServicio;

    //Administracion de empleados (Solo admin)
    @GetMapping("/index")
    public String AdministracionUsuarios(Model model, @RequestParam(name = "nombreUsuario", required = false) String nombreUsuario) {
        List<Usuario> usuarios;
        if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
            try {
                Usuario usuario = usuarioServicio.findBynombre(nombreUsuario);
                if (usuario != null) {
                    usuarios = List.of(usuario);
                } else {
                    model.addAttribute("error", "No se encontro ningun usuario con el nombre: " + nombreUsuario);
                    usuarios = Collections.emptyList();
                }
            } catch (NumberFormatException e) {
                model.addAttribute("error", "El nombre ingresado no es válido.");
                usuarios = Collections.emptyList();
            }
        } else {
            usuarios = usuarioServicio.findAll();
        }

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("nombreUsuario", nombreUsuario);
        if (model.containsAttribute("aviso")) {
            String aviso = (String) model.getAttribute("aviso");
            model.addAttribute("aviso", aviso);
        }

        return "usuarios/index";
    }

    // Editar informacion de usuario (Solo admin)
    @GetMapping("/{correo}/editar")
    public String mostrarFormularioEdicion(@PathVariable("correo") String correo, Model model) {

        Usuario usuario = usuarioServicio.findByCorreo(correo);
        if (usuario == null) {
            return "redirect:/error/404";
        }
        model.addAttribute("usuario", usuario);
        return "usuarios/editarUsuario";
    }

    // Procesar la edicion de un usuario
    @PostMapping("/{correo}/editar")
    public String editarUsuario(@PathVariable("correo") String correo, @ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        Usuario u = usuarioServicio.findByCorreo(correo);
        if (u == null) {
            return "redirect:/error/404";
        }

        if (u.getId() == 1) {
            return "redirect:/usuarios";
        }

        usuario.setId(u.getId());
        usuario.setCorreo(u.getCorreo());
        usuario.setPassword(u.getPassword());

        Rol rol = rolServicio.findByNombre(usuario.getRol().getNombre());
        usuario.setRol(rol);

        usuarioServicio.save(usuario);
        redirectAttributes.addFlashAttribute("aviso", "Usuario actualizado correctamente");
        return "redirect:/usuarios/index";
    }

    // Eliminar un usuario
    @Transactional
    @GetMapping("/{correo}/eliminar")
    public String eliminarUsuario(@PathVariable("correo") String correo, RedirectAttributes redirectAttributes) {

        Usuario usuario = usuarioServicio.findByCorreo(correo);
        if (usuario == null) {
            return "redirect:/error/404";
        }

        if (usuario.getId() == 1) {
            return "redirect:/usuarios";
        }

        usuarioServicio.eliminarUsuario(usuario);
        redirectAttributes.addFlashAttribute("aviso", "Usuario eliminado correctamente");
        return "redirect:/usuarios/index";
    }
}
