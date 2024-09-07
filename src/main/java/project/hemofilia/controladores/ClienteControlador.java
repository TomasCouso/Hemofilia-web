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

import java.util.List;

@Controller
@RequestMapping("/cliente")
public class ClienteControlador {

    @Autowired
    private HistoriaClinicaServicio historiaClinicaServicio;
    @Autowired
    private EpisodioServicio episodioServicio;

    @GetMapping("/historiaClinica/{id}")
    public String verHistoriaClinica(@PathVariable("id") Long id, Model model) {
        HistoriaClinica historiaClinica = historiaClinicaServicio.findHistoriaClinicaById(id);

        if (historiaClinica != null) {
            model.addAttribute("historiaClinica", historiaClinica);
        } else {
            //configurar error
            return "error/404";
        }

        return "cliente/historiaClinica";
    }

    @GetMapping("/historiaClinica/{id}/info")
    public String verInfoPaciente(@PathVariable("id") Long id, Model model) {
        HistoriaClinica historiaClinica = historiaClinicaServicio.findHistoriaClinicaById(id);
        if (historiaClinica != null) {
            model.addAttribute("historiaClinica", historiaClinica); //historia
            List<Episodio> episodios = episodioServicio.findAll(); // Obtener la lista de episodios
            Episodio ultimoEpisodio = episodios.isEmpty() ? null : episodios.get(episodios.size() - 1);
            model.addAttribute("episodios", episodios);
            model.addAttribute("ultimoEpisodio", ultimoEpisodio);

        } else {
            //configurar error
            return "error/404";
        }

        return "cliente/informacion";
    }

    @GetMapping("/historiaClinica/{id}/urgencia")
    public String verPaginaUrgencia(@PathVariable("id") Long id, Model model) {
        HistoriaClinica historiaClinica = historiaClinicaServicio.findHistoriaClinicaById(id);

        if (historiaClinica != null) {
            model.addAttribute("contactoFamiliar", historiaClinica.getTelefonoDeContacto());
        } else {
            //configurar error
            return "error/404";
        }

        return "cliente/urgencia";
    }


}
