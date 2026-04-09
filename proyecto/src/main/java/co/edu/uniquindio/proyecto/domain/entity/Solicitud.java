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
 * clasificación por prioridad, y la gestión de variaciones del proceso
 * como cancelaciones, rechazos y reaperturas.</p>
 *
 * <p>El flujo principal de estados permitido es:
 * REGISTRADA → CLASIFICADA → EN_ATENCION → ATENDIDA → CERRADA.</p>
 *
 * <p>Adicionalmente, el sistema contempla estados alternos como:
 * CANCELADA y RECHAZADA, que representan situaciones reales dentro del
 * ciclo de vida de la solicitud.</p>
 *
 * <p>Situaciones como pausas o esperas se gestionan mediante eventos en el
 * historial, evitando complejidad innecesaria en el modelo de estados.</p>
 *
 * <p>Cada transición registra un evento en el historial para mantener
 * trazabilidad completa del proceso, incluyendo el usuario que ejecuta
 * la acción y el estado en el que se encontraba la solicitud.</p>
 *
 * <p><b>Reglas por rol:</b></p>
 * <ul>
 *     <li><b>Estudiante:</b> Solo puede crear solicitudes y cancelarlas si es el solicitante.</li>
 *     <li><b>Profesor:</b> Puede visualizar solicitudes y atenderlas solo si es responsable.</li>
 *     <li><b>Administrador:</b> Puede clasificar, asignar responsables, cambiar prioridad,
 *     cerrar, rechazar, reasignar y reabrir solicitudes. También puede ser responsable.</li>
 * </ul>
 */
public class Solicitud {

    private final CodigoSolicitud id;
    private final LocalDateTime fechaCreacion;
    private LocalDateTime fechaAtencion;
    private LocalDateTime fechaCierre;

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
     * @param solicitante usuario que crea la solicitud (debe ser estudiante)
     *
     * @throws IllegalArgumentException si la descripción es vacía
     * o el solicitante es nulo o no es estudiante
     */
    public Solicitud(CodigoSolicitud id, TipoSolicitud tipo, String descripcion, CanalOrigen canal, Usuario solicitante) {
        if (descripcion == null || descripcion.isBlank())
            throw new IllegalArgumentException("La descripción es obligatoria");

        if (solicitante == null)
            throw new IllegalArgumentException("El solicitante es obligatorio");

        if (!(solicitante instanceof Estudiante))
            throw new IllegalArgumentException("Solo un estudiante puede crear solicitudes");

        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.canal = canal;
        this.solicitante = solicitante;

        this.estado = EstadoSolicitud.REGISTRADA;
        this.fechaCreacion = LocalDateTime.now();
        this.historial = new ArrayList<>();

        registrarEvento("Solicitud creada por " + solicitante.getIdentificacion() + " vía " + canal, solicitante);
    }

    public List<EventoHistorial> getHistorial() {
        return Collections.unmodifiableList(historial);
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    /**
     * Clasifica la solicitud asignándole una prioridad.
     *
     * <p>Esta acción cambia el estado a
     * {@link EstadoSolicitud#CLASIFICADA} siempre que se encuentre en
     * estado {@link EstadoSolicitud#REGISTRADA}.</p>
     *
     * <p>Solo un {@link Administrador} puede ejecutar esta acción.</p>
     */
    public void clasificar(Prioridad nuevaPrioridad, Usuario actor) {
        if (!(actor instanceof Administrador))
            throw new IllegalStateException("Solo un administrador puede clasificar solicitudes");

        if (this.estado != EstadoSolicitud.REGISTRADA)
            throw new IllegalStateException("Solo se puede clasificar una solicitud registrada");

        this.prioridad = nuevaPrioridad;
        cambiarEstado(EstadoSolicitud.CLASIFICADA);
        registrarEvento("Solicitud clasificada con prioridad: " + nuevaPrioridad.nivel(), actor);
    }

    /**
     * Asigna un responsable encargado de atender la solicitud.
     *
     * <p>Solo un {@link Administrador} puede realizar esta acción.</p>
     *
     * <p>El responsable debe ser un {@link Profesor} o un {@link Administrador} activo.</p>
     */
    public void asignarResponsable(Usuario nuevoResponsable, Usuario actor) {
        if (!(actor instanceof Administrador))
            throw new IllegalStateException("Solo un administrador puede asignar responsables");

        if (this.estado != EstadoSolicitud.CLASIFICADA)
            throw new IllegalStateException("La solicitud debe estar clasificada");

        if (this.prioridad == null)
            throw new IllegalStateException("Debe asignarse una prioridad antes");

        if (!(nuevoResponsable instanceof Profesor) && !(nuevoResponsable instanceof Administrador))
            throw new IllegalStateException("El responsable debe ser profesor o administrador");

        if (!nuevoResponsable.isActivo())
            throw new IllegalStateException("Solo se pueden asignar responsables activos");

        this.responsable = nuevoResponsable;
        cambiarEstado(EstadoSolicitud.EN_ATENCION);
        registrarEvento("Responsable asignado: " + nuevoResponsable.getIdentificacion(), actor);
    }

    /**
     * Marca la solicitud como atendida.
     *
     * <p>Puede ser ejecutado únicamente por el responsable asignado,
     * siempre que sea un {@link Profesor} o un {@link Administrador}.</p>
     *
     * <p>Garantiza que solo quien está a cargo de la solicitud pueda
     * completarla.</p>
     */
    public void marcarComoAtendida(Usuario actor) {
        if (this.estado != EstadoSolicitud.EN_ATENCION)
            throw new IllegalStateException("Solo se puede atender en estado EN_ATENCION");

        if (this.responsable == null)
            throw new IllegalStateException("No hay responsable asignado");

        boolean esResponsable = actor.equals(this.responsable);
        boolean rolValido = (actor instanceof Profesor) || (actor instanceof Administrador);

        if (!esResponsable || !rolValido)
            throw new IllegalStateException("Solo el responsable (profesor o administrador) puede marcar como atendida");

        this.fechaAtencion = LocalDateTime.now();
        cambiarEstado(EstadoSolicitud.ATENDIDA);
        registrarEvento("Solicitud atendida por responsable: " + actor.getIdentificacion(), actor);
    }

    /**
     * Cierra la solicitud.
     *
     * <p>Solo un {@link Administrador} puede cerrar solicitudes.</p>
     */
    public void cerrar(String observacionCierre, Usuario quienCierra) {
        if (!(quienCierra instanceof Administrador))
            throw new IllegalStateException("Solo un Administrador puede cerrar solicitudes");

        if (this.estado != EstadoSolicitud.ATENDIDA)
            throw new IllegalStateException("No se puede cerrar si no ha sido atendida");

        if (observacionCierre == null || observacionCierre.length() < 5)
            throw new IllegalArgumentException("Se requiere una observación válida para el cierre");

        this.fechaCierre = LocalDateTime.now();
        cambiarEstado(EstadoSolicitud.CERRADA);
        registrarEvento("Solicitud cerrada por " + quienCierra.getIdentificacion() + ". Obs: " + observacionCierre, quienCierra);
    }

    /**
     * Cancela la solicitud.
     *
     * <p>Puede hacerlo el estudiante solicitante o un {@link Administrador}.</p>
     */
    public void cancelar(String motivo, Usuario actor) {
        if (this.estado == EstadoSolicitud.CERRADA || this.estado == EstadoSolicitud.ATENDIDA)
            throw new IllegalStateException("No se puede cancelar una solicitud atendida o cerrada");

        boolean esSolicitante = actor.equals(this.solicitante) && actor instanceof Estudiante;
        boolean esAdmin = actor instanceof Administrador;

        if (!esSolicitante && !esAdmin)
            throw new IllegalStateException("Solo el estudiante solicitante o un administrador pueden cancelar");

        if (motivo == null || motivo.isBlank())
            throw new IllegalArgumentException("Debe indicar el motivo de cancelación");

        cambiarEstado(EstadoSolicitud.CANCELADA);
        registrarEvento("Solicitud cancelada. Motivo: " + motivo, actor);
    }

    /**
     * Rechaza la solicitud.
     *
     * <p>Solo un {@link Administrador} puede ejecutar esta acción.</p>
     */
    public void rechazar(String motivo, Usuario actor) {
        if (!(actor instanceof Administrador))
            throw new IllegalStateException("Solo un administrador puede rechazar solicitudes");

        if (this.estado != EstadoSolicitud.CLASIFICADA)
            throw new IllegalStateException("Solo se puede rechazar en estado CLASIFICADA");

        if (motivo == null || motivo.isBlank())
            throw new IllegalArgumentException("Debe indicar el motivo de rechazo");

        cambiarEstado(EstadoSolicitud.RECHAZADA);
        registrarEvento("Solicitud rechazada. Motivo: " + motivo, actor);
    }

    /**
     * Reasigna el responsable.
     *
     * <p>Solo un {@link Administrador} puede ejecutar esta acción.</p>
     */
    public void reasignarResponsable(Usuario nuevoResponsable, Usuario actor) {
        if (!(actor instanceof Administrador))
            throw new IllegalStateException("Solo un administrador puede reasignar responsables");

        if (this.estado != EstadoSolicitud.EN_ATENCION)
            throw new IllegalStateException("Solo se puede reasignar en estado EN_ATENCION");

        if (!(nuevoResponsable instanceof Profesor) && !(nuevoResponsable instanceof Administrador))
            throw new IllegalStateException("El responsable debe ser profesor o administrador");

        if (!nuevoResponsable.isActivo())
            throw new IllegalStateException("El nuevo responsable debe estar activo");

        this.responsable = nuevoResponsable;
        registrarEvento("Responsable reasignado a: " + nuevoResponsable.getIdentificacion(), actor);
    }

    /**
     * Cambia la prioridad.
     *
     * <p>Solo un {@link Administrador} puede ejecutar esta acción.</p>
     */
    public void cambiarPrioridad(Prioridad nuevaPrioridad, Usuario actor) {
        if (!(actor instanceof Administrador))
            throw new IllegalStateException("Solo un administrador puede cambiar la prioridad");

        if (this.estado == EstadoSolicitud.CERRADA)
            throw new IllegalStateException("Una solicitud cerrada no puede modificarse");

        this.prioridad = nuevaPrioridad;
        registrarEvento("Prioridad cambiada a: " + nuevaPrioridad.nivel(), actor);
    }

    /**
     * Reabre una solicitud.
     *
     * <p>Solo un {@link Administrador} puede ejecutar esta acción.</p>
     */
    public void reabrir(String motivo, Usuario actor) {
        if (!(actor instanceof Administrador))
            throw new IllegalStateException("Solo un administrador puede reabrir solicitudes");

        if (this.estado != EstadoSolicitud.CERRADA)
            throw new IllegalStateException("Solo se pueden reabrir solicitudes cerradas");

        if (motivo == null || motivo.isBlank() || motivo.length() < 5)
            throw new IllegalArgumentException("Se requiere un motivo válido para la reapertura");

        cambiarEstado(EstadoSolicitud.EN_ATENCION);
        registrarEvento("Solicitud reabierta por " + actor.getIdentificacion() + ". Motivo: " + motivo, actor);
    }

    /**
     * Cambia el estado de la solicitud.
     */
    private void cambiarEstado(EstadoSolicitud nuevoEstado) {
        this.estado = nuevoEstado;
    }

    /**
     * Registra un evento en el historial.
     */
    private void registrarEvento(String desc, Usuario actor) {
        this.historial.add(new EventoHistorial(LocalDateTime.now(), desc, this.estado, actor));
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setResponsable(Usuario asignado) {
        this.responsable = asignado;
    }
}