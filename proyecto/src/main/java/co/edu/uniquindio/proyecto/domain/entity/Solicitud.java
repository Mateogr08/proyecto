package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.valueobject.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa una solicitud académica dentro del sistema.
 *
 * <p>Esta clase es el agregado principal del dominio encargado de gestionar
 * el ciclo de vida de una solicitud realizada por un usuario. Controla los
 * cambios de estado de la solicitud, la asignación de responsables, la
 * clasificación por prioridad y el registro de eventos en el historial.</p>
 *
 * <p>El flujo de estados permitido es:
 * REGISTRADA → CLASIFICADA → EN_ATENCION → ATENDIDA → CERRADA.</p>
 *
 * <p>Cada transición registra un evento para mantener trazabilidad
 * del proceso de la solicitud.</p>
 */
public class Solicitud {

    private final CodigoSolicitud id;
    private final LocalDateTime fechaCreacion;
    private final TipoSolicitud tipo;
    private final String descripcion;
    private final CanalOrigen canal;
    private final Usuario solicitante;
    private EstadoSolicitud estado;
    private Usuario responsable;
    private Prioridad prioridad;
    private final List<EventoHistorial> historial;

    /**
     * Crea una nueva solicitud en el sistema.
     *
     * <p>Al momento de la creación la solicitud inicia en estado
     * {@link EstadoSolicitud#REGISTRADA} y se registra automáticamente
     * un evento en el historial indicando su creación.</p>
     *
     * @param id identificador único de la solicitud
     * @param tipo tipo de solicitud académica
     * @param descripcion descripción del problema o requerimiento
     * @param canal canal por el cual se originó la solicitud
     * @param solicitante usuario que crea la solicitud
     *
     * @throws IllegalArgumentException si la descripción es vacía
     * o el solicitante es nulo
     */
    public Solicitud(CodigoSolicitud id, TipoSolicitud tipo, String descripcion, CanalOrigen canal, Usuario solicitante) {
        if (descripcion == null || descripcion.isBlank()) throw new IllegalArgumentException("La descripción es obligatoria");
        if (solicitante == null) throw new IllegalArgumentException("El solicitante es obligatorio");

        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.canal = canal;
        this.solicitante = solicitante;
        this.estado = EstadoSolicitud.REGISTRADA;
        this.fechaCreacion = LocalDateTime.now();
        this.historial = new ArrayList<>();
        registrarEvento("Solicitud creada por " + solicitante.getIdentificacion() + " vía " + canal);
    }

    public List<EventoHistorial> getHistorial() { return Collections.unmodifiableList(historial); }
    public EstadoSolicitud getEstado() { return estado; }

    /**
     * Clasifica la solicitud asignándole una prioridad.
     *
     * <p>Esta acción cambia el estado de la solicitud a
     * {@link EstadoSolicitud#CLASIFICADA} y registra un evento
     * en el historial.</p>
     *
     * @param nuevaPrioridad prioridad asignada a la solicitud
     */
    public void clasificar(Prioridad nuevaPrioridad) {
        validarModificable();
        this.prioridad = nuevaPrioridad;
        cambiarEstado(EstadoSolicitud.CLASIFICADA);
        registrarEvento("Solicitud clasificada con prioridad: " + nuevaPrioridad.nivel());
    }

    /**
     * Asigna un responsable encargado de atender la solicitud.
     *
     * <p>El responsable debe estar activo. Al asignarse correctamente,
     * el estado de la solicitud cambia a
     * {@link EstadoSolicitud#EN_ATENCION}.</p>
     *
     * @param nuevoResponsable usuario responsable de atender la solicitud
     *
     * @throws IllegalStateException si el responsable está inactivo
     */
    public void asignarResponsable(Usuario nuevoResponsable) {
        validarModificable();
        if (!nuevoResponsable.isActivo()) {
            throw new IllegalStateException("Solo se pueden asignar responsables activos");
        }
        this.responsable = nuevoResponsable;
        cambiarEstado(EstadoSolicitud.EN_ATENCION);
        registrarEvento("Responsable asignado: " + nuevoResponsable.getIdentificacion());
    }

    /**
     * Marca la solicitud como atendida.
     *
     * <p>Solo puede realizarse si existe un responsable asignado.
     * Cambia el estado a {@link EstadoSolicitud#ATENDIDA} y
     * registra el evento correspondiente.</p>
     *
     * @throws IllegalStateException si no existe responsable asignado
     */
    public void marcarComoAtendida() {
        validarModificable();
        if (this.responsable == null) throw new IllegalStateException("No se puede atender sin un responsable");
        cambiarEstado(EstadoSolicitud.ATENDIDA);
        registrarEvento("La solicitud ha sido marcada como atendida.");
    }

    /**
     * Cierra la solicitud una vez ha sido atendida.
     *
     * <p>Solo un usuario con rol de {@link Administrador} puede
     * cerrar una solicitud. Además se debe proporcionar una
     * observación de cierre válida.</p>
     *
     * @param observacionCierre comentario u observación del cierre
     * @param quienCierra usuario que realiza el cierre
     *
     * @throws IllegalStateException si la solicitud no ha sido atendida
     * o si el usuario no es administrador
     * @throws IllegalArgumentException si la observación es inválida
     */
    public void cerrar(String observacionCierre, Usuario quienCierra) {
        if (this.estado != EstadoSolicitud.ATENDIDA) {
            throw new IllegalStateException("No se puede cerrar si no ha sido atendida");
        }
        if (!(quienCierra instanceof Administrador)) {
            throw new IllegalStateException("Solo un Administrador puede cerrar solicitudes");
        }
        if (observacionCierre == null || observacionCierre.length() < 5) {
            throw new IllegalArgumentException("Se requiere una observación válida para el cierre");
        }

        this.estado = EstadoSolicitud.CERRADA;
        registrarEvento("Solicitud cerrada por " + quienCierra.getIdentificacion() + ". Obs: " + observacionCierre);
    }

    /**
     * Cambia el estado de la solicitud validando que la transición
     * sea permitida dentro del ciclo de vida.
     *
     * @param nuevoEstado nuevo estado al que se desea cambiar
     *
     * @throws IllegalArgumentException si la transición no es válida
     */
    private void cambiarEstado(EstadoSolicitud nuevoEstado) {
        if (!esTransicionValida(nuevoEstado)) {
            throw new IllegalArgumentException("Transición no permitida de " + this.estado + " a " + nuevoEstado);
        }
        this.estado = nuevoEstado;
    }

    /**
     * Verifica si la transición entre estados es válida según
     * el flujo definido del ciclo de vida de la solicitud.
     *
     * @param nuevo estado al que se desea transicionar
     * @return true si la transición es válida, false en caso contrario
     */
    private boolean esTransicionValida(EstadoSolicitud nuevo) {
        return switch (this.estado) {
            case REGISTRADA -> nuevo == EstadoSolicitud.CLASIFICADA;
            case CLASIFICADA -> nuevo == EstadoSolicitud.EN_ATENCION;
            case EN_ATENCION -> nuevo == EstadoSolicitud.ATENDIDA;
            case ATENDIDA -> nuevo == EstadoSolicitud.CERRADA;
            case CERRADA -> false;
        };
    }

    /**
     * Registra un evento en el historial de la solicitud.
     *
     * <p>Los eventos permiten mantener trazabilidad de las
     * acciones realizadas sobre la solicitud.</p>
     *
     * @param desc descripción del evento ocurrido
     */
    private void registrarEvento(String desc) {
        this.historial.add(new EventoHistorial(LocalDateTime.now(), desc, this.estado));
    }

    /**
     * Valida que la solicitud pueda seguir siendo modificada.
     *
     * <p>Una solicitud cerrada no puede cambiar su estado ni
     * recibir modificaciones.</p>
     *
     * @throws IllegalStateException si la solicitud ya está cerrada
     */
    private void validarModificable() {
        if (this.estado == EstadoSolicitud.CERRADA) {
            throw new IllegalStateException("Una solicitud cerrada no puede modificarse");
        }
    }
}