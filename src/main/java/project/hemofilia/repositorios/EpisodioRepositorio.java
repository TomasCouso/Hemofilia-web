package project.hemofilia.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.hemofilia.entidades.Episodio;

import java.util.List;
import java.util.Optional;

public interface EpisodioRepositorio extends JpaRepository<Episodio, Long> {

    Optional<Episodio> findById(Long id);

    @Query("SELECT e FROM Episodio e WHERE e.historiaClinica.numeroHistoriaClinica = :numeroHistoriaClinica")
    List<Episodio> findByNumeroHistoriaClinica(@Param("numeroHistoriaClinica") Long numeroHistoriaClinica);

}
