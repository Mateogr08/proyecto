package co.edu.uniquindio.proyecto.application.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO para marcar una solicitud como atendida.
 */
public record AtenderSolicitudRequest(

        @NotBlank(message = "El ID de la solicitud es obligatorio")
        String idSolicitud

) {}