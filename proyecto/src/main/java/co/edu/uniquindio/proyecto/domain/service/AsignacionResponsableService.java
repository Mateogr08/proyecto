package co.edu.uniquindio.proyecto.domain.service;

import co.edu.uniquindio.proyecto.domain.entity.*;

/**
 * Servicio de dominio encargado de la asignación y reasignación
 * de responsables en solicitudes.
 *
 * <p>Este servicio valida reglas relacionadas con roles de usuario,
 * estado activo del responsable y condiciones necesarias para la
 * asignación.</p>
 */
public class AsignacionResponsableService {

    /**
     * Asigna un responsable a una solicitud.
     *
     * <p>Reglas de negocio:</p>
     * <ul>
     *     <li>Solo un administrador puede asignar responsables.</li>
     *     <li>El responsable debe ser profesor o administrador.</li>
     *     <li>El responsable debe estar activo.</li>
     *     <li>La solicitud debe estar clasificada (validado en la entidad).</li>
     * </ul>
     *
     * @param solicitud solicitud a asignar
     * @param responsable usuario responsable
     * @param actor usuario que ejecuta la acción
     */
    public void asignarResponsable(Solicitud solicitud, Usuario responsable, Usuario actor) {

        if (!actor.esAdministrador()) {
            throw new IllegalStateException("Solo un administrador puede asignar responsables");
        }

        if (!(responsable.esProfesor() || responsable.esAdministrador())) {
            throw new IllegalStateException("El responsable debe ser profesor o administrador");
        }

        if (!responsable.estaActivo()) {
            throw new IllegalStateException("El responsable debe estar activo");
        }

        solicitud.asignarResponsable(responsable);
    }

    /**
     * Reasigna el responsable de una solicitud.
     *
     * <p>Reglas de negocio:</p>
     * <ul>
     *     <li>Solo un administrador puede reasignar responsables.</li>
     *     <li>El nuevo responsable debe estar activo.</li>
     *     <li>La solicitud debe estar en estado EN_ATENCION (validado en la entidad).</li>
     * </ul>
     *
     * @param solicitud solicitud a modificar
     * @param nuevoResponsable nuevo usuario responsable
     * @param actor usuario que ejecuta la acción
     */
    public void reasignarResponsable(Solicitud solicitud, Usuario nuevoResponsable, Usuario actor) {

        if (!actor.esAdministrador()) {
            throw new IllegalStateException("Solo un administrador puede reasignar responsables");
        }

        if (!nuevoResponsable.estaActivo()) {
            throw new IllegalStateException("El nuevo responsable debe estar activo");
        }

        solicitud.reasignarResponsable(nuevoResponsable);
    }
}
