package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import java.util.Objects;

/**
 * Representa un usuario dentro del dominio del sistema.
 *
 * <p>Esta es una clase abstracta que define la estructura y comportamiento
 * común de todos los usuarios, tales como {@link Estudiante},
 * {@link Profesor} y {@link Administrador}.</p>
 *
 * <p>Cada usuario posee una identificación única, información personal básica
 * y un estado que indica si puede participar en las operaciones del sistema.</p>
 *
 * <p>La clase también encapsula la lógica para determinar el tipo de usuario,
 * evitando que capas externas dependan de comparaciones por texto o lógica duplicada.</p>
 */
public abstract class Usuario {

    private final String identificacion;
    private final String nombreCompleto;
    private final Email email;
    private boolean activo;

    /**
     * Crea un nuevo usuario dentro del sistema.
     *
     * <p>Por defecto, todo usuario se crea en estado activo.</p>
     *
     * @param identificacion identificador único del usuario
     * @param nombreCompleto nombre completo del usuario
     * @param email correo electrónico del usuario
     *
     * @throws IllegalArgumentException si alguno de los datos es nulo o inválido
     */
    public Usuario(String identificacion, String nombreCompleto, Email email) {

        if (identificacion == null || identificacion.isBlank()) {
            throw new IllegalArgumentException("La identificación es obligatoria");
        }

        if (nombreCompleto == null || nombreCompleto.isBlank()) {
            throw new IllegalArgumentException("El nombre completo es obligatorio");
        }

        if (email == null) {
            throw new IllegalArgumentException("El email es obligatorio");
        }

        this.identificacion = identificacion;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.activo = true;
    }

    /**
     * Determina si el usuario es un administrador.
     *
     * <p>Este método encapsula la verificación del tipo concreto del usuario,
     * evitando que esta lógica se replique en otras capas del sistema.</p>
     *
     * @return true si el usuario es una instancia de {@link Administrador}
     */
    public boolean esAdministrador() {
        return this instanceof Administrador;
    }

    /**
     * Determina si el usuario es un profesor.
     *
     * @return true si el usuario es una instancia de {@link Profesor}
     */
    public boolean esProfesor() {
        return this instanceof Profesor;
    }

    /**
     * Determina si el usuario es un estudiante.
     *
     * @return true si el usuario es una instancia de {@link Estudiante}
     */
    public boolean esEstudiante() {
        return this instanceof Estudiante;
    }

    /**
     * Indica si el usuario se encuentra activo dentro del sistema.
     *
     * <p>Un usuario inactivo no debería participar en procesos del dominio
     * como asignaciones o gestión de solicitudes.</p>
     *
     * @return true si está activo, false en caso contrario
     */
    public boolean estaActivo() {
        return activo;
    }

    /**
     * Desactiva el usuario.
     *
     * <p>Este cambio afecta su participación en el sistema,
     * pero no elimina su información.</p>
     */
    public void desactivar() {
        this.activo = false;
    }

    /**
     * Activa nuevamente al usuario.
     *
     * <p>Permite que el usuario vuelva a participar en las operaciones del sistema.</p>
     */
    public void activar() {
        this.activo = true;
    }

    /**
     * Obtiene la identificación única del usuario.
     *
     * @return identificación
     */
    public String getIdentificacion() {
        return identificacion;
    }

    /**
     * Obtiene el nombre completo del usuario.
     *
     * @return nombre completo
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return email
     */
    public Email getEmail() {
        return email;
    }

    /**
     * Define la igualdad entre usuarios basada en su identificación única.
     *
     * <p>Esto permite comparar entidades del dominio correctamente,
     * especialmente en colecciones o validaciones.</p>
     *
     * @param o objeto a comparar
     * @return true si ambos representan el mismo usuario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return identificacion.equals(usuario.identificacion);
    }

    /**
     * Genera el hash del usuario basado en su identificación.
     *
     * @return valor hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(identificacion);
    }
}