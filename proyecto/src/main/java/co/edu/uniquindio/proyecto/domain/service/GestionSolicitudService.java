package co.edu.uniquindio.proyecto.domain.service;

import co.edu.uniquindio.proyecto.domain.entity.*;

/**
 * Servicio de dominio encargado de la gestión del ciclo de vida
 * de una solicitud.
 *
 * <p>Incluye operaciones como cierre, cancelación, rechazo y reapertura,
 * aplicando reglas de autorización y coordinación entre usuario y solicitud.</p>
 */
public class GestionSolicitudService {

    /**
     * Cierra una solicitud.
     *
     * <p>Reglas de negocio:</p>
     * <ul>
     * <li>Solo un administrador puede cerrar solicitudes.</li>
     * <li>La solicitud debe estar en estado ATENDIDA (validado en la entidad).</li>
     * <li>Debe existir una observación válida.</li>
     * </ul>
     *
     * @param solicitud solicitud a cerrar
     * @param observacion observación de cierre
     * @param actor usuario que ejecuta la acción
     */
    public void cerrarSolicitud(Solicitud solicitud, String observacion, Usuario actor) {

        if (!actor.esAdministrador()) {
            throw new IllegalStateException("Solo un administrador puede cerrar solicitudes");
        }

        solicitud.cerrar(observacion);
    }

    /**
     * Cancela una solicitud.
     *
     * <p>Reglas de negocio:</p>
     * <ul>
     * <li>Puede cancelar el solicitante o un administrador.</li>
     * <li>No debe estar atendida ni cerrada (validado en la entidad).</li>
     * <li>Debe indicarse un motivo válido.</li>
     * </ul>
     */
    public void cancelarSolicitud(Solicitud solicitud, String motivo, Usuario actor) {

        boolean autorizado = actor.esAdministrador() || actor.equals(solicitud.getSolicitante());

        if (!autorizado) {
            throw new IllegalStateException("No autorizado para cancelar la solicitud");
        }

        solicitud.cancelar(motivo);
    }

    /**
     * Rechaza una solicitud.
     *
     * <p>Reglas de negocio:</p>
     * <ul>
     * <li>Solo un administrador puede rechazar solicitudes.</li>
     * <li>La solicitud debe estar clasificada (validado en la entidad).</li>
     * <li>Debe existir un motivo de rechazo.</li>
     * </ul>
     */
    public void rechazarSolicitud(Solicitud solicitud, String motivo, Usuario actor) {

        if (!actor.esAdministrador()) {
            throw new IllegalStateException("Solo un administrador puede rechazar solicitudes");
        }

        solicitud.rechazar(motivo);
    }

    /**
     * Reabre una solicitud previamente cerrada.
     *
     * <p>Reglas de negocio:</p>
     * <ul>
     * <li>Solo un administrador puede reabrir solicitudes.</li>
     * <li>La solicitud debe estar en estado CERRADA (validado en la entidad).</li>
     * <li>Debe indicarse un motivo válido.</li>
     * </ul>
     */
    public void reabrirSolicitud(Solicitud solicitud, String motivo, Usuario actor) {

        if (!actor.esAdministrador()) {
            throw new IllegalStateException("Solo un administrador puede reabrir solicitudes");
        }

        solicitud.reabrir(motivo);
    }
}
