package project.hemofilia.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import project.hemofilia.entidades.Episodio;
import project.hemofilia.entidades.HistoriaClinica;

import project.hemofilia.servicios.EpisodioServicio;
import project.hemofilia.servicios.HistoriaClinicaServicio;
import project.hemofilia.servicios.TokenServicio;

import java.util.List;

@Controller
@RequestMapping("/cliente")
public class ClienteControlador {

    @Autowired
    private HistoriaClinicaServicio historiaClinicaServicio;

    @Autowired
    private EpisodioServicio episodioServicio;

    @Autowired
    private TokenServicio tokenServicio;

    //Metodo para ver la pagina principal, despues del escaneado del codigo QR
    @GetMapping("/historiaClinica/{token}")
    public String verHistoriaClinica(@PathVariable("token") String token, Model model) {
        try {
            Long id = tokenServicio.obtenerIdClienteDesdeToken(token);
            HistoriaClinica historiaClinica = historiaClinicaServicio.findHistoriaClinicaById(id);

            if (historiaClinica != null) {
                model.addAttribute("historiaClinica", historiaClinica);
            } else {
                return "redirect:/error/404";
            }

            return "cliente/historiaClinica";
        } catch (Exception e) {
            return "redirect:/error/404";
        }
    }

    //Metodo para ver toda la informacion de la historia clinica del paciente y el ultimo episodio
    @GetMapping("/historiaClinica/{token}/info")
    public String verInfoPaciente(@PathVariable("token") String token, Model model) {

        try {
            Long id = tokenServicio.obtenerIdClienteDesdeToken(token);
            HistoriaClinica historiaClinica = historiaClinicaServicio.findHistoriaClinicaById(id);

            if (historiaClinica != null) {
                model.addAttribute("historiaClinica", historiaClinica);
                List<Episodio> episodios = episodioServicio.findEpisodiosPorIdHistoriaClinica(id);
                Episodio ultimoEpisodio = episodios.isEmpty() ? null : episodios.getLast();
                model.addAttribute("ultimoEpisodio", ultimoEpisodio);
            } else {
                return "redirect:/error/404";
            }

            return "cliente/informacion";
        } catch (Exception e) {
            return "redirect:/error/404";
        }


    }

    //Metodo para la vista para realizar llamados en caso de emergencia
    @GetMapping("/historiaClinica/{token}/urgencia")
    public String verPaginaUrgencia(@PathVariable("token") String token, Model model) {

        try {
            Long id = tokenServicio.obtenerIdClienteDesdeToken(token);
            HistoriaClinica historiaClinica = historiaClinicaServicio.findHistoriaClinicaById(id);

            if (historiaClinica != null) {
                model.addAttribute("contactoFamiliar", historiaClinica.getTelefonoDeContacto());
            } else {
                return "redirect:/error/404";
            }

            return "cliente/urgencia";
        } catch (Exception e) {
            return "redirect:/error/404";
        }
    }
}
