package co.edu.uniquindio.proyecto.domain.valueobject;

/**
 * Representa los diferentes tipos de solicitudes académicas que pueden
 * registrarse en el sistema.
 *
 * <p>Este enum actúa como un Value Object que permite categorizar las
 * solicitudes según su naturaleza dentro del dominio académico.</p>
 *
 * <p>Su propósito principal es proporcionar una clasificación semántica
 * de las solicitudes, facilitando su gestión, filtrado y análisis.</p>
 *
 * <p><b>Importante:</b></p>
 * <ul>
 *     <li>No contiene lógica de negocio relacionada con la prioridad.</li>
 *     <li>No define comportamientos dinámicos del sistema.</li>
 *     <li>No toma decisiones sobre el flujo de la solicitud.</li>
 * </ul>
 *
 * <p>La determinación de la prioridad de una solicitud es una responsabilidad
 * externa a este Value Object y puede depender de múltiples factores, como:</p>
 * <ul>
 *     <li>El criterio de un usuario administrador.</li>
 *     <li>Reglas definidas en servicios de dominio.</li>
 *     <li>Sugerencias generadas por sistemas externos (IA).</li>
 * </ul>
 *
 * <p>De esta manera se evita el acoplamiento entre el tipo de solicitud
 * y otras decisiones del negocio, manteniendo un diseño flexible y
 * alineado con los principios de Domain-Driven Design (DDD).</p>
 */
public enum TipoSolicitud {

    /**
     * Solicitud relacionada con el registro o inscripción de asignaturas.
     */
    REGISTRO_ASIGNATURAS("Registro de asignaturas"),

    /**
     * Solicitud de homologación de materias cursadas previamente.
     */
    HOMOLOGACION("Homologación"),

    /**
     * Solicitud para cancelar asignaturas registradas.
     */
    CANCELACION("Cancelación de asignaturas"),

    /**
     * Solicitud para obtener cupo en una asignatura.
     */
    CUPO("Solicitud de cupos"),

    /**
     * Solicitud de consulta general académica.
     */
    CONSULTA("Consulta académica");

    /**
     * Nombre descriptivo del tipo de solicitud.
     */
    private final String nombre;

    /**
     * Constructor del tipo de solicitud.
     *
     * @param nombre nombre descriptivo utilizado para mostrar el tipo
     * en interfaces o reportes
     */
    TipoSolicitud(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el nombre descriptivo del tipo de solicitud.
     *
     * @return nombre del tipo de solicitud
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el tipo de solicitud a partir de su nombre o valor.
     *
     * @param value valor del tipo de solicitud
     * @return tipo de solicitud
     * @throws IllegalArgumentException si el valor no coincide con ningún tipo
     */
    public static TipoSolicitud of(String value) {
        for (TipoSolicitud tipo : TipoSolicitud.values()) {
            if (tipo.name().equalsIgnoreCase(value) || tipo.nombre.equalsIgnoreCase(value)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de solicitud no válido: " + value);
    }
}