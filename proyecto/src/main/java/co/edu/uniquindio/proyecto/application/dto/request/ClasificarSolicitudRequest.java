package co.edu.uniquindio.proyecto.application.dto.request;

import jakarta.validation.constraints.*;

/**
 * DTO para clasificar una solicitud.
 */
public record ClasificarSolicitudRequest(

        @NotBlank(message = "El ID de la solicitud es obligatorio")
        String idSolicitud,

        @NotBlank(message = "El nombre de la prioridad es obligatorio")
        String nombrePrioridad,

        @NotBlank(message = "La justificación es obligatoria")
        @Size(min = 10, message = "Debe tener al menos 10 caracteres")
        String justificacion,

        @NotBlank(message = "El actor es obligatorio")
        String idActor

) {}