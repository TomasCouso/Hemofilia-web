package project.hemofilia.controladores;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorControlador implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String mensaje = "Ocurrió un error inesperado";

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                mensaje = "Página no encontrada";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                mensaje = "Error interno del servidor";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                mensaje = "Acceso denegado";
            }
        }
        model.addAttribute("error", mensaje);
        return "error";
    }
}