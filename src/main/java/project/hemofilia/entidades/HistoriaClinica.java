package project.hemofilia.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "historia_clinica")
public class HistoriaClinica {

    @Id
    @Column(name = "numero_historia_clinica")
    private Long numeroHistoriaClinica;

    @Column(name = "fecha_historia_clinica", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaHistoriaClinica;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "dni", nullable = false, unique = true)
    private int dni;

    @Column(name = "telefonoDeContacto", nullable = false)
    private String telefonoDeContacto;

    @Column(name = "fecha_nacimiento", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaNacimiento;

    // edad

    @Column(name = "obra_social")
    private String obraSocial;

    @Column(name = "numero_de_afiliado")
    private Long numeroDeAfiliado;

    @Column(name = "fecha_diagnostico")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaDiagnostico;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "ultimo_titulo")
    private double ultimoTitulo;

    @Column(name = "fecha_ultimo_titulo")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaUltimoTitulo;

    @Column(name = "tipo_de_tratamiento", nullable = false)
    private String tipoDeTratamiento;

    @Column(name = "inhibidor", nullable = false)
    private boolean inhibidor;

    @Column(name = "estrategias_trapeuticas")
    private String estrategiasTrapeuticas;

    @Column(name = "peso", nullable = false)
    private int peso;

    @Column(name = "dosis", nullable = false)
    private int dosis;

    // dosis ui/kg = dosis/peso

    @Column(name = "observaciones")
    private String observaciones;

}
