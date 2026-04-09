package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import java.util.Objects;

/**
 * Representa un usuario del sistema.
 *
 * <p>Esta es una clase abstracta que define los atributos y comportamientos
 * comunes para los diferentes tipos de usuarios del sistema, como
 * {@link Estudiante}, {@link Profesor} y {@link Administrador}.</p>
 *
 * <p>Cada usuario posee una identificación única, un nombre completo,
 * una dirección de correo electrónico y un estado que indica si el
 * usuario se encuentra activo o no dentro del sistema.</p>
 *
 * <p>Las clases que extienden esta clase deben definir el rol específico
 * del usuario.</p>
 */
public abstract class Usuario {

    private final String identificacion;
    private final String nombreCompleto;
    private final Email email;
    private boolean activo;

    /**
     * Crea un nuevo usuario en el sistema.
     *
     * <p>Al momento de crearse, el usuario queda activo por defecto.</p>
     *
     * @param identificacion identificador único del usuario
     * @param nombreCompleto nombre completo del usuario
     * @param email dirección de correo electrónico del usuario
     *
     * @throws IllegalArgumentException si los datos son inválidos
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
     * Retorna el rol del usuario dentro del sistema.
     *
     * <p>Debe ser implementado por las clases hijas para indicar
     * el tipo de usuario (Estudiante, Profesor, Administrador).</p>
     *
     * @return nombre del rol del usuario
     */
    public abstract String getRol();

    /**
     * Indica si el usuario se encuentra activo.
     *
     * @return true si está activo, false en caso contrario
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * Obtiene la identificación del usuario.
     *
     * @return identificación única
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
     * Desactiva el usuario en el sistema.
     *
     * <p>Un usuario inactivo no debería participar en procesos
     * operativos como asignaciones o atención de solicitudes.</p>
     */
    public void desactivar() {
        this.activo = false;
    }

    /**
     * Activa el usuario en el sistema.
     *
     * <p>Permite que el usuario vuelva a participar en las operaciones.</p>
     */
    public void activar() {
        this.activo = true;
    }

    /**
     * Define igualdad entre usuarios basada en la identificación única.
     *
     * <p>Esto es fundamental para comparar usuarios dentro del dominio,
     * especialmente en validaciones como asignaciones de responsables.</p>
     *
     * @param o objeto a comparar
     * @return true si representan el mismo usuario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return identificacion.equals(usuario.identificacion);
    }

    /**
     * Genera el hash basado en la identificación.
     *
     * @return hash del usuario
     */
    @Override
    public int hashCode() {
        return Objects.hash(identificacion);
    }
}
