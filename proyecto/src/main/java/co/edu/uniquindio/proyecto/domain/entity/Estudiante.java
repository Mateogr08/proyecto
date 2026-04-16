package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.valueobject.Email;

/**
 * Representa un estudiante dentro del sistema.
 *
 * <p>Un estudiante es un tipo de usuario que puede crear solicitudes
 * académicas y hacer seguimiento a las mismas.</p>
 *
 * <p>Hereda las propiedades y comportamientos básicos de {@link Usuario},
 * como identificación, nombre, correo electrónico y estado.</p>
 */
public class Estudiante extends Usuario {

    /**
     * Crea un nuevo estudiante.
     *
     * @param identificacion identificador único del estudiante
     * @param nombreCompleto nombre completo del estudiante
     * @param email correo electrónico del estudiante
     */
    public Estudiante(String identificacion, String nombreCompleto, Email email) {
        super(identificacion, nombreCompleto, email);
    }
}
