package project.hemofilia.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hemofilia.entidades.HistoriaClinica;

public interface HistoriaClinicaRepositorio extends JpaRepository<HistoriaClinica, Long> {

}
