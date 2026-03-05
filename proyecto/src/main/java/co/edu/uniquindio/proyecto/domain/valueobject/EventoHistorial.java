package co.edu.uniquindio.proyecto.domain.valueobject;

import java.time.LocalDateTime;

public record EventoHistorial(
        LocalDateTime fecha,
        String descripcion,
        EstadoSolicitud estadoRelacionado
) {
    public EventoHistorial {
        if (fecha == null) fecha = LocalDateTime.now();
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción del evento es obligatoria");
        }
    }
}