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
    private EstadoSolicitud estado;
    private Usuario responsable;
    private Prioridad prioridad;
    private final List<EventoHistorial> historial;

    public Solicitud(CodigoSolicitud id, TipoSolicitud tipo) {
        this.id = id;
        this.tipo = tipo;
        this.estado = EstadoSolicitud.REGISTRADA;
        this.fechaCreacion = LocalDateTime.now();
        this.historial = new ArrayList<>();
        registrarEvento("Solicitud creada en el sistema");
    }

    public List<EventoHistorial> getHistorial() {
        return Collections.unmodifiableList(historial);
    }

    public void marcarComoAtendida() {
        validarModificable();
        cambiarEstado(EstadoSolicitud.ATENDIDA);
        registrarEvento("La solicitud ha sido marcada como atendida por el responsable.");
    }

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

    public void cerrar(String observacionCierre) {
        if (this.estado != EstadoSolicitud.ATENDIDA) {
            throw new IllegalStateException("No se puede cerrar si no ha sido atendida");
        }
        if (observacionCierre == null || observacionCierre.isBlank()) {
            throw new IllegalArgumentException("Se requiere una observación para el cierre");
        }
        this.estado = EstadoSolicitud.CERRADA;
        registrarEvento("Solicitud cerrada: " + observacionCierre);
    }

    private void cambiarEstado(EstadoSolicitud nuevoEstado) {
        if (!esTransicionValida(nuevoEstado)) {
            throw new IllegalArgumentException("Transición no permitida a " + nuevoEstado);
        }
        this.estado = nuevoEstado;
    }

    private void registrarEvento(String descripcion) {
        this.historial.add(new EventoHistorial(LocalDateTime.now(), descripcion, this.estado));
    }

    private void validarModificable() {
        if (this.estado == EstadoSolicitud.CERRADA) {
            throw new IllegalStateException("Una solicitud cerrada no puede modificarse"); // B.4 Invariante
        }
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
}