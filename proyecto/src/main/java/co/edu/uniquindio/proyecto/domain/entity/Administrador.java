package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.valueobject.Email;

/**
 * Representa un administrador dentro del sistema.
 *
 * <p>El administrador es un usuario con privilegios elevados,
 * encargado de gestionar el sistema y tomar decisiones clave
 * dentro del flujo de solicitudes, como asignaciones o cambios de estado.</p>
 *
 * <p>Hereda los atributos y comportamientos comunes definidos en {@link Usuario}.</p>
 */
public class Administrador extends Usuario {

    /**
     * Crea un nuevo administrador.
     *
     * @param identificacion identificador único del administrador
     * @param nombreCompleto nombre completo del administrador
     * @param email correo electrónico del administrador
     */
    public Administrador(String identificacion, String nombreCompleto, Email email) {
        super(identificacion, nombreCompleto, email);
    }
}