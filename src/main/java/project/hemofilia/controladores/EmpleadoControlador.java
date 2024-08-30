package project.hemofilia.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.hemofilia.entidades.Episodio;
import project.hemofilia.entidades.HistoriaClinica;
import project.hemofilia.servicios.EpisodioServicio;
import project.hemofilia.servicios.HistoriaClinicaServicio;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/empleado")
public class EmpleadoControlador {


    @Autowired
    private HistoriaClinicaServicio historiaClinicaServicio;

    @Autowired
    private EpisodioServicio episodioServicio;

    // Página inicial del empleado: lista de todas las historias clínicas
    @GetMapping("/historiasClinicas")
    public String listarHistoriasClinicas(Model model) {
        List<HistoriaClinica> historiasClinicas = historiaClinicaServicio.findAll();
        model.addAttribute("historiasClinicas", historiasClinicas);
        return "empleado/historiasClinicas";
    }

    // Ver una historia clínica específica
    @GetMapping("/historiasClinicas/{id}")
    public String verHistoriaClinica(@PathVariable("id") Long id, Model model) {
        HistoriaClinica historiaClinica = historiaClinicaServicio.findHistoriaClinicaById(id);
        model.addAttribute("historiaClinica", historiaClinica);
        return "verHistoriaClinica";
    }

    // Mostrar el formulario para editar una historia clínica
    @GetMapping("/historiasClinicas/{id}/editar")
    public String mostrarFormularioEdicion(@PathVariable("id") Long id, Model model) {
        HistoriaClinica historiaClinica = historiaClinicaServicio.findHistoriaClinicaById(id);
        model.addAttribute("historiaClinica", historiaClinica);
        return "empleado/editarHistoriaClinica";
    }

    // Procesar la edición de una historia clínica
    @PostMapping("/historiasClinicas/{id}/editar")
    public String editarHistoriaClinica(@PathVariable("id") Long id, @ModelAttribute HistoriaClinica historiaClinica) {
        historiaClinicaServicio.actualizarHistoriaClinica(historiaClinica);
        return "redirect:/empleado/historiasClinicas";
    }

    // Mostrar el formulario para crear una nueva historia clínica
    @GetMapping("/historiasClinicas/nueva")
    public String mostrarFormularioCreacion(Model model) {
        model.addAttribute("historiaClinica", new HistoriaClinica());
        return "empleado/nuevaHistoriaClinica";
    }

    // Procesar la creación de una nueva historia clínica
    @PostMapping("/historiasClinicas")
    public String crearHistoriaClinica(@ModelAttribute HistoriaClinica historiaClinica) {
        historiaClinicaServicio.crearHistoriaClinica(historiaClinica);
        return "redirect:/empleado/historiasClinicas";
    }

    // Eliminar una historia clínica
    @GetMapping("/historiasClinicas/{id}/eliminar")
    public String eliminarHistoriaClinica(@PathVariable("id") Long id) {
        historiaClinicaServicio.eliminarHistoriaClinica(id);
        return "redirect:/empleado/historiasClinicas";
    }

    // Mostrar el formulario para crear un nuevo evento en una historia clínica
    @GetMapping("/historiasClinicas/{historiaClinicaId}/episodio/nuevo")
    public String mostrarFormularioCreacionEpisodio(@PathVariable("historiaClinicaId") Long historiaClinicaId, Model model) {
        model.addAttribute("episodio", new Episodio());
        model.addAttribute("historiaClinicaId", historiaClinicaId);
        return "empleado/nuevoEpisodio";
    }

    // Procesar la creación de un nuevo evento
    @PostMapping("/historiasClinicas/{historiaClinicaId}/episodio")
    public String crearEpisodio(@PathVariable("historiaClinicaId") Long historiaClinicaId,
                              @ModelAttribute Episodio episodio) {
        episodio.setHistoriaClinica(historiaClinicaServicio.findHistoriaClinicaById(historiaClinicaId));
        episodioServicio.crearEpisodio(episodio);
        return "redirect:/empleado/historiasClinicas/" + historiaClinicaId;
    }

    // Mostrar el formulario para editar un evento dentro de una historia clínica
    @GetMapping("/historiasClinicas/{historiaClinicaId}/episodios/{episodioId}/editar")
    public String mostrarFormularioEdicionEpisodio(@PathVariable("historiaClinicaId") Long historiaClinicaId,
                                                 @PathVariable("episodioId") Long episodioId, Model model) {
        Optional<Episodio> episodio = episodioServicio.findEpisodioPorId(episodioId);
        model.addAttribute("episodio", episodio);
        return "empleado/editarEpisodio";
    }

    // Procesar la edición de un evento
    @PostMapping("/historiasClinicas/{historiaClinicaId}/episodios/{episodioId}/editar")
    public String editarEpisodio(@PathVariable("historiaClinicaId") Long historiaClinicaId,
                               @PathVariable("episodioId") Long episodioId,
                               @ModelAttribute Episodio episodio) {
        episodioServicio.actualizarEpisodio(episodio);
        return "redirect:/empleado/historiasClinicas/" + historiaClinicaId;
    }

}