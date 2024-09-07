package project.hemofilia.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

//Inserción de datos de ejemplo en la tabla Episodio
//INSERT INTO episodio (fecha_episodio, episodio_hemorragico, descripcion_tratamiento, episodio_en_curso, numero_historia_clinica)
//VALUES ('2023-07-01', 'Hemorragia leve', 'Aplicación de factor VIII', false, 1001);


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "episodio")
public class Episodio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_episodio")
    private Long id;

    @Column(name = "fecha_episodio", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaEpisodio;

    @Column(name= "episodio_hemorragico", nullable = false)
    private String episodioHemorragico;

    @Column(name = "descripcion_tratamiento", nullable = false)
    private String descripcionTratamiento;

    @Column(name= "episodio_en_curso")
    private Boolean episodioEnCurso;

    @ManyToOne
    @JoinColumn(name = "numero_historia_clinica", referencedColumnName = "numero_historia_clinica", insertable = false, updatable = false)
    private HistoriaClinica historiaClinica;
}
