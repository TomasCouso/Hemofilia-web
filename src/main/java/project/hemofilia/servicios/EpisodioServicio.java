package project.hemofilia.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.hemofilia.entidades.Episodio;
import project.hemofilia.repositorios.EpisodioRepositorio;

import java.util.List;

@Service
public class EpisodioServicio {

    @Autowired
    private EpisodioRepositorio episodioRepositorio;

    public List<Episodio> getEpisodiosPorIdHistoriaClinica(Long id) {
        return episodioRepositorio.findByNumeroHistoriaClinica(id);
    }
}
