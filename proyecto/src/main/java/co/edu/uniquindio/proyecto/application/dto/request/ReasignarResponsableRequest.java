package co.edu.uniquindio.proyecto.application.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO para reasignar el responsable de una solicitud.
 *
 * <p>Permite cambiar el usuario encargado de la atención.</p>
 */
public record ReasignarResponsableRequest(

        @NotBlank(message = "El ID de la solicitud es obligatorio")
        String idSolicitud,

        @NotBlank(message = "El nuevo responsable es obligatorio")
        String nuevoResponsableId,

        @NotBlank(message = "El actor es obligatorio")
        String idActor

) {}