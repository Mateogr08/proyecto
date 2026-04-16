package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.valueobject.Email;

/**
 * Representa un profesor dentro del sistema.
 *
 * <p>Un profesor es un tipo de usuario que puede encargarse de la
 * gestión y atención de solicitudes académicas, dependiendo del flujo
 * definido en el sistema.</p>
 *
 * <p>Hereda las propiedades comunes desde {@link Usuario}.</p>
 */
public class Profesor extends Usuario {

    /**
     * Crea un nuevo profesor.
     *
     * @param identificacion identificador único del profesor
     * @param nombreCompleto nombre completo del profesor
     * @param email correo electrónico del profesor
     */
    public Profesor(String identificacion, String nombreCompleto, Email email) {
        super(identificacion, nombreCompleto, email);
    }
}