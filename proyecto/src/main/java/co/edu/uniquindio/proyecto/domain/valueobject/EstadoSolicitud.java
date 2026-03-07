package co.edu.uniquindio.proyecto.domain.valueobject;

/**
 * Representa los posibles estados del ciclo de vida de una solicitud
 * dentro del sistema.
 *
 * <p>Los estados definen las diferentes etapas por las que pasa una
 * solicitud desde su creación hasta su cierre. Las transiciones entre
 * estados siguen un flujo controlado por las reglas del dominio.</p>
 *
 * <p>El flujo típico de estados es:
 * REGISTRADA → CLASIFICADA → EN_ATENCION → ATENDIDA → CERRADA.</p>
 *
 * <p>Cada estado indica el progreso de la gestión de la solicitud
 * dentro del sistema.</p>
 */

public enum EstadoSolicitud {
    REGISTRADA,
    CLASIFICADA,
    EN_ATENCION,
    ATENDIDA,
    CERRADA;
}