package project.hemofilia.controladores;

import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.hemofilia.entidades.Episodio;
import project.hemofilia.entidades.HistoriaClinica;
import project.hemofilia.servicios.EpisodioServicio;
import project.hemofilia.servicios.HistoriaClinicaServicio;
import project.hemofilia.servicios.TokenServicio;
import project.hemofilia.utils.GeneradorQR;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/empleado")
public class EmpleadoControlador {


    @Autowired
    private HistoriaClinicaServicio historiaClinicaServicio;

    @Autowired
    private EpisodioServicio episodioServicio;

    @Autowired
    private TokenServicio tokenServicio;

    private GeneradorQR generadorQR;

    // Página inicial del empleado: lista de todas las historias clínicas
    // hay que agregar busqueda
    @GetMapping("/historiasClinicas")
    public String listarHistoriasClinicas(Model model, @RequestParam(name = "numeroHistoria", required = false) String numeroHistoria) {
        List<HistoriaClinica> historiasClinicas;
        if (numeroHistoria != null && !numeroHistoria.isEmpty()) {
            try {
                long id = Long.parseLong(numeroHistoria);
                HistoriaClinica historia = historiaClinicaServicio.findHistoriaClinicaById(id);
                if (historia != null) {
                    historiasClinicas = List.of(historia);
                } else {
                    model.addAttribute("error", "No se encontro ninguna historia clínica con el id: " + numeroHistoria);
                    historiasClinicas = Collections.emptyList();
                }
            } catch (NumberFormatException e) {
                model.addAttribute("error", "El número de historia clínica ingresado no es válido.");
                historiasClinicas = Collections.emptyList();
            }
        } else {
            historiasClinicas = historiaClinicaServicio.findAll();
        }

        model.addAttribute("historiasClinicas", historiasClinicas);
        model.addAttribute("numeroHistoria", numeroHistoria);
        return "empleado/historiasClinicas";
    }


    // Ver una historia clínica específica
    @GetMapping("/historiaClinica/{id}")
    public String verHistoriaClinica(@PathVariable("id") Long id, Model model) {

        HistoriaClinica historiaClinica = historiaClinicaServicio.findHistoriaClinicaById(id);
        if (historiaClinica == null) {
            return "redirect:/error/404";
        }

        List<Episodio> episodios = episodioServicio.findEpisodiosPorIdHistoriaClinica(id);
        model.addAttribute("historiaClinica", historiaClinica);
        model.addAttribute("episodios", episodios);
        return "empleado/verInformacion";
    }

    // Mostrar el formulario para editar una historia clínica
    @GetMapping("/historiaClinica/{id}/editar")
    public String mostrarFormularioEdicion(@PathVariable("id") Long id, Model model) {

        HistoriaClinica historiaClinica = historiaClinicaServicio.findHistoriaClinicaById(id);
        if (historiaClinica == null) {
            return "redirect:/error/404";
        }
        model.addAttribute("historiaClinica", historiaClinica);
        return "empleado/editarHistoriaClinica";
    }

    // Procesar la edición de una historia clínica
    @PostMapping("/historiaClinica/{id}/editar")
    public String editarHistoriaClinica(@PathVariable("id") Long id, @ModelAttribute HistoriaClinica
            historiaClinica) {

        HistoriaClinica h =historiaClinicaServicio.findHistoriaClinicaById(id);
        if (h == null) {
            return "redirect:/error/404";
        }

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
        HistoriaClinica h =historiaClinicaServicio.findHistoriaClinicaById(historiaClinica.getNumeroHistoriaClinica());
        if (h != null) {
            return "redirect:/error/404";
        }
        historiaClinicaServicio.crearHistoriaClinica(historiaClinica);
        return "redirect:/empleado/historiasClinicas";
    }

    // Eliminar una historia clínica
    @Transactional
    @GetMapping("/historiaClinica/eliminar/{id}")
    public String eliminarHistoriaClinica(@PathVariable("id") Long id) {

        HistoriaClinica historiaClinica =historiaClinicaServicio.findHistoriaClinicaById(id);
        if (historiaClinica == null) {
            return "redirect:/error/404";
        }

        historiaClinicaServicio.eliminarHistoriaClinica(id);
        return "redirect:/empleado/historiasClinicas";
    }

    // Mostrar el formulario para crear un nuevo evento en una historia clínica
    @GetMapping("/historiaClinica/{historiaClinicaId}/episodio/nuevo")
    public String mostrarFormularioCreacionEpisodio(@PathVariable("historiaClinicaId") Long
                                                            historiaClinicaId, Model model) {

        HistoriaClinica historiaClinica =historiaClinicaServicio.findHistoriaClinicaById(historiaClinicaId);
        if (historiaClinica == null) {
            return "redirect:/error/404";
        }

        model.addAttribute("episodio", new Episodio());
        model.addAttribute("historiaClinicaId", historiaClinicaId);
        return "empleado/nuevoEpisodio";
    }

    // Procesar la creación de un nuevo evento
    @PostMapping("/historiaClinica/{historiaClinicaId}/episodio/nuevo")
    public String crearEpisodio(@PathVariable("historiaClinicaId") Long historiaClinicaId,
                                @ModelAttribute Episodio episodio) {

        HistoriaClinica historiaClinica =historiaClinicaServicio.findHistoriaClinicaById(historiaClinicaId);
        if (historiaClinica == null) {
            return "redirect:/error/404";
        }

        episodio.setHistoriaClinica(historiaClinicaServicio.findHistoriaClinicaById(historiaClinicaId));
        episodioServicio.crearEpisodio(episodio);
        return "redirect:/empleado/historiaClinica/" + historiaClinicaId;
    }

    // Mostrar el formulario para editar un evento dentro de una historia clínica
    @GetMapping("/historiaClinica/{historiaClinicaId}/episodio/editar/{episodioId}")
    public String mostrarFormularioEdicionEpisodio(@PathVariable("historiaClinicaId") Long historiaClinicaId,
                                                   @PathVariable("episodioId") Long episodioId, Model model) {

        HistoriaClinica historiaClinica =historiaClinicaServicio.findHistoriaClinicaById(historiaClinicaId);
        Episodio episodio = episodioServicio.findEpisodioPorId(episodioId);
        if (historiaClinica == null || episodio == null) {
            return "redirect:/error/404";
        }

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

        HistoriaClinica h =historiaClinicaServicio.findHistoriaClinicaById(historiaClinicaId);
        Episodio e = episodioServicio.findEpisodioPorId(episodioId);
        if (h == null || e != null) {
            return "redirect:/error/404";
        }

        episodio.setHistoriaClinica(historiaClinicaServicio.findHistoriaClinicaById(historiaClinicaId));
        episodio.setId(episodioId);
        episodioServicio.actualizarEpisodio(episodio);
        return "redirect:/empleado/historiaClinica/" + historiaClinicaId;
    }

    // Eliminar evento de historia clinica
    @GetMapping("historiaClinica/{id}/episodio/eliminar/{id_episodio}")
    public String eliminarEventoDeHistoria(@PathVariable("id") Long idHistoria, @PathVariable("id_episodio") Long
            idEpisodio) {

        HistoriaClinica historiaClinica =historiaClinicaServicio.findHistoriaClinicaById(idHistoria);
        Episodio episodio = episodioServicio.findEpisodioPorId(idEpisodio);
        if (historiaClinica == null || episodio == null) {
            return "redirect:/error/404";
        }

        episodioServicio.eliminarEpisodio(idEpisodio);
        return "redirect:/empleado/historiaClinica/" + idHistoria;
    }

    //Creador de la imagen qr
    @GetMapping("/crearQR/{id}")
    public String crearQR(@PathVariable("id") Long id, Model model) {
        HistoriaClinica historiaClinica = historiaClinicaServicio.findHistoriaClinicaById(id);

        if (historiaClinica == null) {
            return "redirect:/error/404";
        }

        // Generar token con el id de la historia clínica
        String token = tokenServicio.generarToken(id);

        // Crear el QR con el token generado
        String textoQR = "http://192.168.0.10:8080/cliente/historiaClinica/" + token; // URL con el token
        String nombreArchivoQR = "qr_historia_" + id + ".png";

        // Obtener la ruta de la carpeta de descargas
        String carpetaDescargas = System.getProperty("user.home") + File.separator + "Downloads";

        try {
            GeneradorQR.generarQR(textoQR, 300, 300, carpetaDescargas + File.separator + nombreArchivoQR);
            model.addAttribute("mensaje", "QR generado exitosamente");
        } catch (WriterException | IOException e) {
            model.addAttribute("error", "Error al generar el QR");
            return "redirect:/empleado/historiaClinica/" + id;
        }

        // Redirigir de nuevo a la página de la historia clínica con el mensaje
        return "redirect:/empleado/historiaClinica/" + id;
    }
}