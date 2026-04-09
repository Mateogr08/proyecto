package co.edu.uniquindio.proyecto.application.dto.request;

import jakarta.validation.constraints.*;

/**
 * DTO para cambiar el estado de una solicitud
 * Mapea a: Solicitud.cambiarEstado()
 *
 * Si el estado es REABIERTA, el motivo es obligatorio
 */
public record CambiarEstadoRequest(

        @NotNull(message = "El nuevo estado es obligatorio")
        String nuevoEstado,

        @Size(max = 500, message = "El motivo no puede superar los 500 caracteres")
        String motivo
) {}