package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.valueobject.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Agregado raíz que representa una solicitud académica dentro del sistema.
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

    // ===================== GETTERS =====================

    public CodigoSolicitud getId() { return id; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaAtencion() { return fechaAtencion; }
    public LocalDateTime getFechaCierre() { return fechaCierre; }
    public TipoSolicitud getTipo() { return tipo; }
    public String getDescripcion() { return descripcion; }
    public CanalOrigen getCanal() { return canal; }
    public Usuario getResponsable() { return responsable; }
    public Prioridad getPrioridad() { return prioridad; }
    public List<EventoHistorial> getHistorial() { return Collections.unmodifiableList(historial); }
    public EstadoSolicitud getEstado() { return estado; }
    public Usuario getSolicitante() { return solicitante; }

    // ===================== CONSTRUCTOR =====================

    public Solicitud(CodigoSolicitud id, TipoSolicitud tipo, String descripcion,
                     CanalOrigen canal, Usuario solicitante) {

        if (id == null) throw new IllegalArgumentException("El ID es obligatorio");
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

        registrarEvento("Solicitud creada", solicitante);
    }

    /**
     * Constructor para reconstrucción desde persistencia.
     */
    public Solicitud(CodigoSolicitud id, LocalDateTime fechaCreacion, LocalDateTime fechaAtencion, 
                     LocalDateTime fechaCierre, TipoSolicitud tipo, String descripcion, 
                     CanalOrigen canal, Usuario solicitante, EstadoSolicitud estado, 
                     Usuario responsable, Prioridad prioridad, List<EventoHistorial> historial) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.fechaAtencion = fechaAtencion;
        this.fechaCierre = fechaCierre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.canal = canal;
        this.solicitante = solicitante;
        this.estado = estado;
        this.responsable = responsable;
        this.prioridad = prioridad;
        this.historial = new ArrayList<>(historial);
    }

    // ===================== MÉTODOS DE NEGOCIO =====================

    public void clasificar(Prioridad prioridad, Usuario actor) {
        if (this.estado != EstadoSolicitud.REGISTRADA)
            throw new IllegalStateException("Solo se puede clasificar desde REGISTRADA");
        if (prioridad == null) throw new IllegalArgumentException("La prioridad es obligatoria");
        if (actor == null) throw new IllegalArgumentException("El actor es obligatorio");

        this.prioridad = prioridad;
        cambiarEstado(EstadoSolicitud.CLASIFICADA, "Solicitud clasificada", actor);
    }

    public void asignarResponsable(Usuario responsable, Usuario actor) {
        if (this.estado != EstadoSolicitud.CLASIFICADA)
            throw new IllegalStateException("Debe estar clasificada");
        if (responsable == null) throw new IllegalArgumentException("Responsable requerido");
        if (actor == null) throw new IllegalArgumentException("El actor es obligatorio");

        this.responsable = responsable;
        cambiarEstado(EstadoSolicitud.EN_ATENCION, "Responsable asignado: " + responsable.getNombreCompleto(), actor);
    }

    public void marcarComoAtendida(Usuario actor) {
        if (this.estado != EstadoSolicitud.EN_ATENCION)
            throw new IllegalStateException("Estado inválido para atender");
        if (this.responsable == null) throw new IllegalStateException("Debe tener responsable");
        if (actor == null) throw new IllegalArgumentException("El actor es obligatorio");

        this.fechaAtencion = LocalDateTime.now();
        cambiarEstado(EstadoSolicitud.ATENDIDA, "Solicitud atendida", actor);
    }

    public void cerrar(String observacion, Usuario actor) {
        if (this.estado != EstadoSolicitud.ATENDIDA)
            throw new IllegalStateException("Debe estar atendida para cerrar");
        if (observacion == null || observacion.isBlank()) throw new IllegalArgumentException("Observación requerida");
        if (actor == null) throw new IllegalArgumentException("El actor es obligatorio");

        this.fechaCierre = LocalDateTime.now();
        cambiarEstado(EstadoSolicitud.CERRADA, "Solicitud cerrada: " + observacion, actor);
    }

    public void cancelar(String motivo, Usuario actor) {
        if (this.estado == EstadoSolicitud.CERRADA || this.estado == EstadoSolicitud.ATENDIDA)
            throw new IllegalStateException("No se puede cancelar una solicitud finalizada");
        if (motivo == null || motivo.isBlank()) throw new IllegalArgumentException("Motivo requerido");
        if (actor == null) throw new IllegalArgumentException("El actor es obligatorio");

        cambiarEstado(EstadoSolicitud.CANCELADA, "Solicitud cancelada: " + motivo, actor);
    }

    public void rechazar(String motivo, Usuario actor) {
        if (this.estado != EstadoSolicitud.CLASIFICADA)
            throw new IllegalStateException("Debe estar clasificada para rechazar");
        if (motivo == null || motivo.isBlank()) throw new IllegalArgumentException("Motivo requerido");
        if (actor == null) throw new IllegalArgumentException("El actor es obligatorio");

        cambiarEstado(EstadoSolicitud.RECHAZADA, "Solicitud rechazada: " + motivo, actor);
    }

    public void reasignarResponsable(Usuario nuevo, Usuario actor) {
        if (this.estado != EstadoSolicitud.EN_ATENCION)
            throw new IllegalStateException("Estado inválido para reasignar");
        if (nuevo == null) throw new IllegalArgumentException("Nuevo responsable requerido");
        if (actor == null) throw new IllegalArgumentException("El actor es obligatorio");

        this.responsable = nuevo;
        registrarEvento("Responsable reasignado: " + nuevo.getNombreCompleto(), actor);
    }

    public void cambiarPrioridad(Prioridad nueva, Usuario actor) {
        if (this.estado == EstadoSolicitud.CERRADA)
            throw new IllegalStateException("No se puede modificar una solicitud cerrada");
        if (nueva == null) throw new IllegalArgumentException("Prioridad requerida");
        if (actor == null) throw new IllegalArgumentException("El actor es obligatorio");

        this.prioridad = nueva;
        registrarEvento("Prioridad actualizada", actor);
    }

    public void reabrir(String motivo, Usuario actor) {
        if (this.estado != EstadoSolicitud.CERRADA)
            throw new IllegalStateException("Debe estar cerrada para reabrir");
        if (motivo == null || motivo.isBlank()) throw new IllegalArgumentException("Motivo requerido");
        if (actor == null) throw new IllegalArgumentException("El actor es obligatorio");

        cambiarEstado(EstadoSolicitud.EN_ATENCION, "Solicitud reabierta: " + motivo, actor);
    }

    // ===================== MÉTODOS INTERNOS =====================

    private void cambiarEstado(EstadoSolicitud nuevoEstado, String descripcion, Usuario actor) {
        this.estado = nuevoEstado;
        registrarEvento(descripcion, actor);
    }

    private void registrarEvento(String desc, Usuario actor) {
        this.historial.add(new EventoHistorial(LocalDateTime.now(), desc, this.estado, actor));
    }
}