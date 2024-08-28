package project.hemofilia.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.hemofilia.entidades.HistoriaClinica;
import project.hemofilia.repositorios.HistoriaClinicaRepositorio;

@Service
public class HistoriaClinicaServicio {

    @Autowired
    private HistoriaClinicaRepositorio historiaClinicaRepositorio;


    public HistoriaClinica getHistoriaClinicaPorId(Long id) {
        return historiaClinicaRepositorio.findById(id).orElse(null);
    }



}
