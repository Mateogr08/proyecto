package co.edu.uniquindio.proyecto.domain.service;
import co.edu.uniquindio.proyecto.domain.entity.*;
import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;

/**
 * Servicio de dominio encargado de la clasificación de solicitudes.
 *
 * <p>Este servicio gestiona la asignación de prioridad a una solicitud,
 * asegurando que únicamente usuarios con rol de administrador puedan
 * ejecutar esta acción.</p>
 *
 * <p>Centraliza las reglas de negocio relacionadas con la clasificación,
 * separando las responsabilidades de autorización del agregado raíz.</p>
 */


public class ClasificadorSolicitudService {

    /**
     * Clasifica una solicitud asignándole una prioridad.
     *
     * <p>Reglas de negocio:</p>
     * <ul>
     * <li>Solo un usuario con rol de administrador puede clasificar solicitudes.</li>
     * <li>La prioridad debe ser válida (no nula).</li>
     * <li>La solicitud debe encontrarse en estado REGISTRADA (validado en la entidad).</li>
     * </ul>
     *
     * @param solicitud solicitud a clasificar
     * @param prioridad prioridad a asignar
     * @param actor usuario que ejecuta la acción
     *
     * @throws IllegalStateException si el usuario no es administrador
     * @throws IllegalArgumentException si la prioridad es inválida
     */
    public void clasificarSolicitud(Solicitud solicitud, Prioridad prioridad, Usuario actor) {

        if (!actor.esAdministrador()) {
            throw new IllegalStateException("Solo un administrador puede clasificar solicitudes");
        }

        solicitud.clasificar(prioridad);
    }
}
