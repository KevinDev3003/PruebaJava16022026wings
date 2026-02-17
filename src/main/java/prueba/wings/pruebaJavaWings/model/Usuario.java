package prueba.wings.pruebaJavaWings.model;

import jakarta.persistence.*;

@Entity
@Table(name = "USUARIOS")
public class Usuario {
    @Id
    @Column(name = "ID_USUARIO", length = 30)
    private String idUsuario;

    @Column(name = "NOMBRE_USUARIO", length = 255, nullable = false)
    private String nombreUsuario;

    @Column(name = "CONTRASENA", length = 30, nullable = false)
    private String contrasena;

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
