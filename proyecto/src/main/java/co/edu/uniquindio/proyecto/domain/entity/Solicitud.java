package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.valueobject.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa una solicitud académica dentro del sistema.
 *
 * <p>Esta clase es el agregado raíz del dominio y encapsula
 * exclusivamente las reglas internas relacionadas con el estado
 * y comportamiento de la solicitud.</p>
 *
 * <p>No contiene lógica de autorización ni validaciones de roles,
 * las cuales deben ser gestionadas por servicios de dominio o aplicación.</p>
 *
 * <p>Flujo de estados:
 * REGISTRADA → CLASIFICADA → EN_ATENCION → ATENDIDA → CERRADA</p>
 *
 * <p>Estados alternos:
 * CANCELADA, RECHAZADA</p>
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

    public CodigoSolicitud getId() {
        return id;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getFechaAtencion() {
        return fechaAtencion;
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    public TipoSolicitud getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public CanalOrigen getCanal() {
        return canal;
    }

    public Usuario getResponsable() {
        return responsable;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public List<EventoHistorial> getHistorial() {
        return historial;
    }

    /**
     * Obtiene el estado actual de la solicitud.
     *
     * @return estado de la solicitud
     */
    public EstadoSolicitud getEstado() {
        return estado;
    }

    /**
     * Obtiene el usuario solicitante.
     *
     * @return usuario que creó la solicitud
     */
    public Usuario getSolicitante() {
        return solicitante;
    }

    public Solicitud(CodigoSolicitud id, TipoSolicitud tipo, String descripcion,
                     CanalOrigen canal, Usuario solicitante) {

        if (descripcion == null || descripcion.isBlank())
            throw new IllegalArgumentException("La descripción es obligatoria");

        if (solicitante == null)
            throw new IllegalArgumentException("El solicitante es obligatorio");

        if (!solicitante.esEstudiante())
            throw new IllegalArgumentException("Solo un estudiante puede crear solicitudes");

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

    public void clasificar(Prioridad prioridad) {
        if (this.estado != EstadoSolicitud.REGISTRADA)
            throw new IllegalStateException("Solo se puede clasificar desde REGISTRADA");

        if (prioridad == null)
            throw new IllegalArgumentException("La prioridad es obligatoria");

        this.prioridad = prioridad;
        cambiarEstado(EstadoSolicitud.CLASIFICADA);
    }

    public void asignarResponsable(Usuario responsable) {
        if (this.estado != EstadoSolicitud.CLASIFICADA)
            throw new IllegalStateException("Debe estar clasificada");

        if (responsable == null)
            throw new IllegalArgumentException("Responsable requerido");

        this.responsable = responsable;
        cambiarEstado(EstadoSolicitud.EN_ATENCION);
    }

    public void marcarComoAtendida() {
        if (this.estado != EstadoSolicitud.EN_ATENCION)
            throw new IllegalStateException("Estado inválido");

        if (this.responsable == null)
            throw new IllegalStateException("Debe tener responsable");

        this.fechaAtencion = LocalDateTime.now();
        cambiarEstado(EstadoSolicitud.ATENDIDA);
    }

    public void cerrar(String observacion) {
        if (this.estado != EstadoSolicitud.ATENDIDA)
            throw new IllegalStateException("Debe estar atendida");

        if (observacion == null || observacion.isBlank())
            throw new IllegalArgumentException("Observación requerida");

        this.fechaCierre = LocalDateTime.now();
        cambiarEstado(EstadoSolicitud.CERRADA);
    }

    public void cancelar(String motivo) {
        if (this.estado == EstadoSolicitud.CERRADA || this.estado == EstadoSolicitud.ATENDIDA)
            throw new IllegalStateException("No se puede cancelar");

        if (motivo == null || motivo.isBlank())
            throw new IllegalArgumentException("Motivo requerido");

        cambiarEstado(EstadoSolicitud.CANCELADA);
    }

    public void rechazar(String motivo) {
        if (this.estado != EstadoSolicitud.CLASIFICADA)
            throw new IllegalStateException("Debe estar clasificada");

        if (motivo == null || motivo.isBlank())
            throw new IllegalArgumentException("Motivo requerido");

        cambiarEstado(EstadoSolicitud.RECHAZADA);
    }

    public void reasignarResponsable(Usuario nuevo) {
        if (this.estado != EstadoSolicitud.EN_ATENCION)
            throw new IllegalStateException("Estado inválido");

        this.responsable = nuevo;
    }

    public void cambiarPrioridad(Prioridad nueva) {
        if (this.estado == EstadoSolicitud.CERRADA)
            throw new IllegalStateException("No se puede modificar");

        this.prioridad = nueva;
    }

    public void reabrir(String motivo) {
        if (this.estado != EstadoSolicitud.CERRADA)
            throw new IllegalStateException("Debe estar cerrada");

        if (motivo == null || motivo.isBlank())
            throw new IllegalArgumentException("Motivo requerido");

        cambiarEstado(EstadoSolicitud.EN_ATENCION);
    }

    private void cambiarEstado(EstadoSolicitud nuevoEstado) {
        this.estado = nuevoEstado;
    }

    private void registrarEvento(String desc, Usuario actor) {
        this.historial.add(new EventoHistorial(LocalDateTime.now(), desc, this.estado, actor));
    }
}
