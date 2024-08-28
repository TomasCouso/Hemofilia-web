package project.hemofilia.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import project.hemofilia.entidades.HistoriaClinica;
import project.hemofilia.servicios.EpisodioServicio;
import project.hemofilia.servicios.HistoriaClinicaServicio;

@Controller
public class ClienteControlador {

    @Autowired
    private HistoriaClinicaServicio historiaClinicaServicio;
    @Autowired
    private EpisodioServicio episodioServicio;

    @GetMapping("/historiaClinica/{id}")
    public String verHistoriaClinica(@PathVariable("id") Long id, Model model) {
        HistoriaClinica historiaClinica = historiaClinicaServicio.getHistoriaClinicaPorId(id);

        if (historiaClinica != null) {
            model.addAttribute("nombrePaciente", historiaClinica.getNombre());
            model.addAttribute("documento", historiaClinica.getDni());
            model.addAttribute("numeroHistoriaClinica", historiaClinica.getNumeroHistoriaClinica());
        } else {
            //configurar error
            return "error/404";
        }

        return "cliente/historiaClinica";
    }

    @GetMapping("/historiaClinica/{id}/info")
    public String verInfoPaciente(@PathVariable("id") Long id, Model model) {
        HistoriaClinica historiaClinica = historiaClinicaServicio.getHistoriaClinicaPorId(id);
        if (historiaClinica != null) {
            model.addAttribute("historiaClinica",historiaClinica); //historia
            model.addAttribute("episodios", episodioServicio.getEpisodiosPorIdHistoriaClinica(id)); //lista de episodios
        } else {
            return "error/404";
        }

        return "cliente/infoPaciente";
    }

    @GetMapping("/historia-clinica/{id}/urgencia")
    public String verPaginaUrgencia(@PathVariable("id") Long id, Model model) {
        HistoriaClinica historiaClinica = historiaClinicaServicio.getHistoriaClinicaPorId(id);

        if (historiaClinica != null) {
            model.addAttribute("contactoFamiliar", historiaClinica.getTelefonoDeContacto());
        } else {
            return "error/404";
        }

        return "cliente/urgencia";
    }



}
