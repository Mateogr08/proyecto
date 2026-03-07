package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.valueobject.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public void clasificar(Prioridad nuevaPrioridad) {
        validarModificable();
        this.prioridad = nuevaPrioridad;
        cambiarEstado(EstadoSolicitud.CLASIFICADA);
        registrarEvento("Solicitud clasificada con prioridad: " + nuevaPrioridad.nivel());
    }

    public void asignarResponsable(Usuario nuevoResponsable) {
        validarModificable();
        if (!nuevoResponsable.isActivo()) {
            throw new IllegalStateException("Solo se pueden asignar responsables activos");
        }
        this.responsable = nuevoResponsable;
        cambiarEstado(EstadoSolicitud.EN_ATENCION);
        registrarEvento("Responsable asignado: " + nuevoResponsable.getIdentificacion());
    }

    public void marcarComoAtendida() {
        validarModificable();
        if (this.responsable == null) throw new IllegalStateException("No se puede atender sin un responsable");
        cambiarEstado(EstadoSolicitud.ATENDIDA);
        registrarEvento("La solicitud ha sido marcada como atendida.");
    }

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

    private void cambiarEstado(EstadoSolicitud nuevoEstado) {
        if (!esTransicionValida(nuevoEstado)) {
            throw new IllegalArgumentException("Transición no permitida de " + this.estado + " a " + nuevoEstado);
        }
        this.estado = nuevoEstado;
    }

    private boolean esTransicionValida(EstadoSolicitud nuevo) {
        return switch (this.estado) {
            case REGISTRADA -> nuevo == EstadoSolicitud.CLASIFICADA;
            case CLASIFICADA -> nuevo == EstadoSolicitud.EN_ATENCION;
            case EN_ATENCION -> nuevo == EstadoSolicitud.ATENDIDA;
            case ATENDIDA -> nuevo == EstadoSolicitud.CERRADA;
            case CERRADA -> false;
        };
    }

    private void registrarEvento(String desc) {
        this.historial.add(new EventoHistorial(LocalDateTime.now(), desc, this.estado));
    }

    private void validarModificable() {
        if (this.estado == EstadoSolicitud.CERRADA) {
            throw new IllegalStateException("Una solicitud cerrada no puede modificarse");
        }
    }
}