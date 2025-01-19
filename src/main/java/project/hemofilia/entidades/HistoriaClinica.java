package project.hemofilia.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

//Inserción de datos de ejemplo en la tabla HistoriaClinica
//INSERT INTO historia_clinica (numero_historia_clinica, fecha_historia_clinica, nombre, apellido, dni, sexo, telefono_de_contacto, fecha_nacimiento, obra_social, numero_de_afiliado, fecha_diagnostico, tipo, ultimo_titulo, fecha_ultimo_titulo, tipo_de_tratamiento, inhibidor, estrategias_trapeuticas, peso, tipo_de_factor, dosis1, dosis2, observaciones)
//VALUES (1001, '2022-01-15', 'Carlos', 'Lopez', 12345678, 'Masculino', '123456789', '1990-05-10', 'OSDE', 456789, '2005-08-20', 'Hemofilia', 12.5, '2023-04-15', 'Tratamiento A', true, 'Tratamiento personalizado', 75, 'Factor VIII', 250, 500, 'Paciente estable');


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

    @Column(name = "sexo", nullable = false)
    private String sexo;

    @Column(name = "telefonoDeContacto", nullable = false)
    private String telefonoDeContacto;

    @Column(name = "fecha_nacimiento", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaNacimiento;

    // edad

    @Column(name = "obra_social")
    private String obraSocial;

    @Column(name = "numero_de_afiliado")
    private String numeroDeAfiliado;

    @Column(name = "fecha_diagnostico")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaDiagnostico;

    @Column(name = "tipo", nullable = false)
    private String tipoHemofilia;

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

    @Column(name = "tipo_de_factor", nullable = false)
    private String tipoDeFactor;

    @Column(name = "dosis1", nullable = false)
    private int dosis1;

    @Column(name = "dosis2", nullable = false)
    private int dosis2;

    // dosis ui/kg = dosis/peso

    @Column(name = "observaciones")
    private String observaciones;

}
