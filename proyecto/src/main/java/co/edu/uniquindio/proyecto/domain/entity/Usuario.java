package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.valueobject.Email;

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
     * @throws IllegalArgumentException si la identificación es nula o vacía
     */

    public Usuario(String identificacion, String nombreCompleto, Email email) {
        if (identificacion == null || identificacion.isBlank()) {
            throw new IllegalArgumentException("La identificación es obligatoria");
        }
        this.identificacion = identificacion;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.activo = true;
    }

    public abstract String getRol();
    public boolean isActivo() { return activo; }
    public String getIdentificacion() { return identificacion; }
    public void desactivar() {this.activo = false;
    }
}
