package project.hemofilia.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.hemofilia.entidades.HistoriaClinica;
import project.hemofilia.repositorios.HistoriaClinicaRepositorio;

import java.util.Collections;
import java.util.List;

@Service
public class HistoriaClinicaServicio {

    @Autowired
    private HistoriaClinicaRepositorio historiaClinicaRepositorio;
    @Autowired
    private EpisodioServicio episodioServicio;

    public HistoriaClinica findHistoriaClinicaById(Long id) {
        return historiaClinicaRepositorio.findById(id).orElse(null);
    }

    public List<HistoriaClinica> findAll() {
        return historiaClinicaRepositorio.findAll();
    }

    public void crearHistoriaClinica(HistoriaClinica historiaClinica) {
        historiaClinicaRepositorio.save(historiaClinica);
    }

    public void actualizarHistoriaClinica(HistoriaClinica historiaClinica) {
        historiaClinicaRepositorio.save(historiaClinica);
    }

    public void eliminarHistoriaClinica(Long id) {
        episodioServicio.eliminarEpisodiosPorHistoriaClinica(id);
        historiaClinicaRepositorio.deleteById(id);
    }
}
