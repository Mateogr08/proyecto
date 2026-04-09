package co.edu.uniquindio.proyecto.domain.valueobject;

/**
 * Representa los posibles estados del ciclo de vida de una solicitud
 * dentro del sistema.
 *
 * <p>Los estados definen las diferentes etapas por las que pasa una
 * solicitud desde su creación hasta su cierre. Las transiciones entre
 * estados siguen un flujo controlado por las reglas del dominio.</p>
 *
 * <p><b>Flujo principal de estados:</b></p>
 * <pre>
 * REGISTRADA → CLASIFICADA → EN_ATENCION → ATENDIDA → CERRADA
 * </pre>
 *
 * <p><b>Estados alternos:</b></p>
 * <ul>
 *     <li><b>CANCELADA:</b> La solicitud fue cancelada por el estudiante solicitante
 *     o un administrador antes de finalizar el proceso.</li>
 *
 *     <li><b>RECHAZADA:</b> La solicitud no cumple con los criterios necesarios
 *     durante la etapa de clasificación.</li>
 * </ul>
 *
 * <p>Cada estado indica el progreso de la gestión de la solicitud
 * dentro del sistema y permite aplicar reglas específicas según la etapa.</p>
 */
public enum EstadoSolicitud {

    /**
     * Estado inicial de la solicitud.
     *
     * <p>Indica que la solicitud ha sido creada por un estudiante
     * pero aún no ha sido clasificada.</p>
     */
    REGISTRADA,

    /**
     * La solicitud ha sido analizada y se le ha asignado una prioridad.
     */
    CLASIFICADA,

    /**
     * La solicitud ha sido asignada a un responsable y está en proceso de atención.
     */
    EN_ATENCION,

    /**
     * La solicitud ha sido atendida por el responsable asignado
     * (profesor o administrador).
     */
    ATENDIDA,

    /**
     * La solicitud ha sido cerrada formalmente por un administrador.
     */
    CERRADA,

    /**
     * La solicitud ha sido cancelada por el estudiante solicitante
     * o un administrador antes de completarse.
     */
    CANCELADA,

    /**
     * La solicitud ha sido rechazada durante la etapa de clasificación
     * por no cumplir con los criterios necesarios.
     */
    RECHAZADA
}