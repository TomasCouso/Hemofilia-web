package project.hemofilia.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.hemofilia.servicios.UsuarioServicio;

@Controller
@RequestMapping()
public class LoginControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/")
    public String redirigirLogin() {
        return "login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        long count = usuarioServicio.contarUsuarios();
        if (count == 0) {
            return "redirect:/registro";
        }
        return "login";
    }

    @GetMapping("/default")
    public String defaultAfterLogin(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            return "redirect:/empleado/historiasClinicas";
        } else {
            return "redirect:/access-denied";
        }
    }
}
