package project.hemofilia.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.hemofilia.entidades.Episodio;
import project.hemofilia.repositorios.EpisodioRepositorio;

import java.util.List;
import java.util.Optional;

@Service
public class EpisodioServicio {

    @Autowired
    private EpisodioRepositorio episodioRepositorio;

    public List<Episodio> findEpisodiosPorIdHistoriaClinica(Long id) {
        return episodioRepositorio.findByNumeroHistoriaClinica(id);
    }

    public void crearEpisodio(Episodio episodio) {
        episodioRepositorio.save(episodio);
    }

    public Optional<Episodio> findEpisodioPorId(Long episodioId) {
        return episodioRepositorio.findById(episodioId);
    }

    public void actualizarEpisodio(Episodio episodio) {
        episodioRepositorio.save(episodio);
    }

    public List<Episodio> findAll() {
        return episodioRepositorio.findAll();
    }

    public void eliminarEpisodio(Long idEpisodio) {
        episodioRepositorio.deleteById(idEpisodio);
    }

    public void eliminarEpisodiosPorHistoriaClinica(Long id) {
        episodioRepositorio.deleteByNumeroHistoriaClinica(id);
    }
}
