package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.valueobject.CodigoSolicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.EstadoSolicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoSolicitud;

import java.time.LocalDateTime;

public class Solicitud {
    private final CodigoSolicitud id;
    private final LocalDateTime fechaCreacion;
    private EstadoSolicitud estado;
    private Usuario responsable;
    private final TipoSolicitud tipo;
    private Prioridad prioridad;

    public Solicitud(CodigoSolicitud id, TipoSolicitud tipo, String descripcion) {
        this.id = id;
        this.tipo = tipo;
        this.estado = EstadoSolicitud.REGISTRADA;
        this.fechaCreacion = LocalDateTime.now();
    }

    // Regla de Negocio: Asignar Responsable
    public void asignarResponsable(Usuario nuevoResponsable) {
        if (!nuevoResponsable.isActivo()) {
            throw new IllegalStateException("Solo se pueden asignar responsables activos");
        }
        this.responsable = nuevoResponsable;
        this.estado = EstadoSolicitud.EN_ATENCION;
    }

    // Regla de Negocio: Cambiar Estado
    public void cambiarEstado(EstadoSolicitud nuevoEstado) {
        if (this.estado == EstadoSolicitud.CERRADA) {
            throw new IllegalStateException("No se pueden realizar cambios en una solicitud ya cerrada");
        }

        if (!esTransicionValida(nuevoEstado)) {
            throw new IllegalArgumentException(
                    "Transición no permitida: de " + this.estado + " a " + nuevoEstado
            );
        }

        this.estado = nuevoEstado;
    }

    // Regla de Negocio: Cerrar Solicitud
    public void cerrar(String observacionCierre) {
        if (this.estado != EstadoSolicitud.ATENDIDA) {
            throw new IllegalStateException("No se puede cerrar si no ha sido atendida");
        }
        if (observacionCierre == null || observacionCierre.isBlank()) {
            throw new IllegalArgumentException("Se requiere una observación para el cierre");
        }
        this.estado = EstadoSolicitud.CERRADA;
    }

    //Lógica para validar el flujo del ciclo de vida
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
