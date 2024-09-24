package project.hemofilia.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.hemofilia.entidades.Episodio;
import project.hemofilia.entidades.HistoriaClinica;
import project.hemofilia.servicios.EpisodioServicio;
import project.hemofilia.servicios.HistoriaClinicaServicio;

import java.util.List;

@Controller
@RequestMapping("/empleado")
public class EmpleadoControlador {


    @Autowired
    private HistoriaClinicaServicio historiaClinicaServicio;

    @Autowired
    private EpisodioServicio episodioServicio;

    // Página inicial del empleado: lista de todas las historias clínicas
    // hay que agregar busqueda
    @GetMapping("/historiasClinicas")
    public String listarHistoriasClinicas(Model model) {
        List<HistoriaClinica> historiasClinicas = historiaClinicaServicio.findAll();
        model.addAttribute("historiasClinicas", historiasClinicas);
        return "empleado/historiasClinicas";
    }

    // Ver una historia clínica específica
    @GetMapping("/historiaClinica/{id}")
    public String verHistoriaClinica(@PathVariable("id") Long id, Model model) {
        HistoriaClinica historiaClinica = historiaClinicaServicio.findHistoriaClinicaById(id);
        List<Episodio> episodios = episodioServicio.findEpisodiosPorIdHistoriaClinica(id);
        model.addAttribute("historiaClinica", historiaClinica);
        model.addAttribute("episodios", episodios);
        return "empleado/verInformacion";
    }

    // Mostrar el formulario para editar una historia clínica
    @GetMapping("/historiaClinica/{id}/editar")
    public String mostrarFormularioEdicion(@PathVariable("id") Long id, Model model) {
        HistoriaClinica historiaClinica = historiaClinicaServicio.findHistoriaClinicaById(id);
        model.addAttribute("historiaClinica", historiaClinica);
        return "empleado/editarHistoriaClinica";
    }

    // Procesar la edición de una historia clínica
    @PostMapping("/historiaClinica/{id}/editar")
    public String editarHistoriaClinica(@PathVariable("id") Long id, @ModelAttribute HistoriaClinica historiaClinica) {
        historiaClinicaServicio.actualizarHistoriaClinica(historiaClinica);
        return "redirect:/empleado/historiasClinicas";
    }

    // Mostrar el formulario para crear una nueva historia clínica
    @GetMapping("/historiaClinica/nueva")
    public String mostrarFormularioCreacion(Model model) {
        model.addAttribute("historiaClinica", new HistoriaClinica());
        return "empleado/nuevaHistoriaClinica";
    }

    // Procesar la creación de una nueva historia clínica
    @PostMapping("/historiaClinica/nueva")
    public String crearHistoriaClinica(@ModelAttribute HistoriaClinica historiaClinica) {
        historiaClinicaServicio.crearHistoriaClinica(historiaClinica);
        return "redirect:/empleado/historiasClinicas";
    }

    // Eliminar una historia clínica
    @Transactional
    @GetMapping("/historiaClinica/eliminar/{id}")
    public String eliminarHistoriaClinica(@PathVariable("id") Long id) {
        historiaClinicaServicio.eliminarHistoriaClinica(id);
        return "redirect:/empleado/historiasClinicas";
    }

    // Mostrar el formulario para crear un nuevo evento en una historia clínica
    @GetMapping("/historiaClinica/{historiaClinicaId}/episodio/nuevo")
    public String mostrarFormularioCreacionEpisodio(@PathVariable("historiaClinicaId") Long historiaClinicaId, Model model) {
        model.addAttribute("episodio", new Episodio());
        model.addAttribute("historiaClinicaId", historiaClinicaId);
        return "empleado/nuevoEpisodio";
    }

    // Procesar la creación de un nuevo evento
    @PostMapping("/historiaClinica/{historiaClinicaId}/episodio/nuevo")
    public String crearEpisodio(@PathVariable("historiaClinicaId") Long historiaClinicaId,
                              @ModelAttribute Episodio episodio) {
        episodio.setHistoriaClinica(historiaClinicaServicio.findHistoriaClinicaById(historiaClinicaId));
        episodioServicio.crearEpisodio(episodio);
        return "redirect:/empleado/historiaClinica/" + historiaClinicaId;
    }

    // Mostrar el formulario para editar un evento dentro de una historia clínica
    @GetMapping("/historiaClinica/{historiaClinicaId}/episodio/editar/{episodioId}")
    public String mostrarFormularioEdicionEpisodio(@PathVariable("historiaClinicaId") Long historiaClinicaId,
                                                 @PathVariable("episodioId") Long episodioId, Model model) {
        // Buscar el episodio y manejar el caso donde no exista
        Episodio episodio = episodioServicio.findEpisodioPorId(episodioId)
                .orElseThrow(() -> new IllegalArgumentException("Episodio no encontrado con id: " + episodioId));
        model.addAttribute("episodio", episodio);
        model.addAttribute("historiaClinicaId", historiaClinicaId);
        model.addAttribute("episodioId", episodioId);
        return "empleado/editarEpisodio";
    }

    // Procesar la edición de un evento
    @PostMapping("/historiaClinica/{historiaClinicaId}/episodio/editar/{episodioId}")
    public String editarEpisodio(@PathVariable("historiaClinicaId") Long historiaClinicaId,
                               @PathVariable("episodioId") Long episodioId,
                               @ModelAttribute Episodio episodio) {
        episodio.setHistoriaClinica(historiaClinicaServicio.findHistoriaClinicaById(historiaClinicaId));
        episodio.setId(episodioId);
        episodioServicio.actualizarEpisodio(episodio);
        return "redirect:/empleado/historiaClinica/" + historiaClinicaId;
    }

    // Eliminar evento de historia clinica
    @GetMapping("historiaClinica/{id}/episodio/eliminar/{id_episodio}")
    public String eliminarEventoDeHistoria(@PathVariable("id") Long idHistoria, @PathVariable("id_episodio") Long idEpisodio) {
        episodioServicio.eliminarEpisodio(idEpisodio);
        return "redirect:/empleado/historiaClinica/" + idHistoria;
    }
}