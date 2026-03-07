package co.edu.uniquindio.proyecto.domain.valueobject;

/**
 * Representa los diferentes tipos de solicitudes académicas que pueden
 * registrarse en el sistema.
 *
 * <p>Cada tipo de solicitud contiene un nombre descriptivo y una prioridad
 * sugerida que puede ser utilizada por los servicios de clasificación para
 * determinar la importancia inicial del trámite.</p>
 *
 * <p>Este valor permite categorizar las solicitudes y facilitar su gestión
 * dentro del sistema.</p>
 */

public enum TipoSolicitud {

    REGISTRO_ASIGNATURAS("Registro de asignaturas", "ALTA"),
    HOMOLOGACION("Homologación", "BAJA"),
    CANCELACION("Cancelación de asignaturas", "MEDIA"),
    CUPO("Solicitud de cupos", "ALTA"),
    CONSULTA("Consulta académica", "BAJA");

    private final String nombre;
    private final String prioridadSugerida;

    /**
     * Crea un tipo de solicitud con su nombre descriptivo
     * y una prioridad sugerida.
     *
     * @param nombre nombre descriptivo del tipo de solicitud
     * @param prioridadSugerida nivel de prioridad sugerido
     * para este tipo de trámite
     */
    TipoSolicitud(String nombre, String prioridadSugerida) {
        this.nombre = nombre;
        this.prioridadSugerida = prioridadSugerida;
    }

    public String getNombre() {return nombre;}
    public String getPrioridadSugerida() {return prioridadSugerida;}
}