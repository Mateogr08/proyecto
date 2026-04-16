package co.edu.uniquindio.proyecto.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para cancelar una solicitud.
 *
 * <p>Debe incluir un motivo válido de cancelación.</p>
 */
public record CancelarSolicitudRequest(

        @NotBlank(message = "El ID de la solicitud es obligatorio")
        String idSolicitud,

        @NotBlank(message = "El motivo es obligatorio")
        @Size(min = 10, max = 500,
                message = "El motivo debe tener entre 10 y 500 caracteres")
        String motivo,

        @NotBlank(message = "El actor es obligatorio")
        String idActor

) {}
