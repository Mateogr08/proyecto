package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import co.edu.uniquindio.proyecto.domain.valueobject.Rol;
import java.util.Objects;

/**
 * Representa un usuario dentro del dominio del sistema.
 */
public abstract class Usuario {

    private final String identificacion;
    private final String nombreCompleto;
    private final Email email;
    private final String password;
    private final Rol rol;
    private boolean activo;

    public Usuario(String identificacion, String nombreCompleto, Email email, String password, Rol rol) {

        if (identificacion == null || identificacion.isBlank()) {
            throw new IllegalArgumentException("La identificación es obligatoria");
        }
        if (nombreCompleto == null || nombreCompleto.isBlank()) {
            throw new IllegalArgumentException("El nombre completo es obligatorio");
        }
        if (email == null) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("El password es obligatorio");
        }
        if (rol == null) {
            throw new IllegalArgumentException("El rol es obligatorio");
        }

        this.identificacion = identificacion;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.activo = true;
    }

    public boolean esAdministrador() {
        return rol == Rol.ADMINISTRADOR;
    }

    public boolean esProfesor() {
        return rol == Rol.PROFESOR;
    }

    public boolean esEstudiante() {
        return rol == Rol.ESTUDIANTE;
    }

    public boolean estaActivo() {
        return activo;
    }

    public void desactivar() {
        this.activo = false;
    }

    public void activar() {
        this.activo = true;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public Email getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Rol getRol() {
        return rol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return identificacion.equals(usuario.identificacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificacion);
    }
}
