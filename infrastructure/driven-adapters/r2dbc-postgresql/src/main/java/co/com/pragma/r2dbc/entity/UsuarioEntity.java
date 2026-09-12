package co.com.pragma.r2dbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table("usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UsuarioEntity implements Persistable<String> {

    @Id
    private String id;

    private String nombres;
    private String apellidos;

    @Column("numero_documento")
    private String numeroDocumento;

    @Column("fecha_nacimiento")
    private LocalDate fechaNacimiento;

    private String direccion;
    private String telefono;
    private String correo;
    private String contrasena;

    @Column("salario_base")
    private BigDecimal salarioBase;

    @Column("id_rol")
    private String idRol;

    // Método de la interfaz Persistable para saber si es un registro nuevo
    @Override
    public boolean isNew() {
        return this.id == null;
    }
}