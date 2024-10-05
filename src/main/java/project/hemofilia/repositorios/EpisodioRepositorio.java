package project.hemofilia.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.hemofilia.entidades.Episodio;

import java.util.List;

public interface EpisodioRepositorio extends JpaRepository<Episodio, Long> {

    @Query("SELECT e FROM Episodio e WHERE e.historiaClinica.numeroHistoriaClinica = :numeroHistoriaClinica")
    List<Episodio> findByNumeroHistoriaClinica(@Param("numeroHistoriaClinica") Long numeroHistoriaClinica);

    void deleteByNumeroHistoriaClinica(Long id);
}
