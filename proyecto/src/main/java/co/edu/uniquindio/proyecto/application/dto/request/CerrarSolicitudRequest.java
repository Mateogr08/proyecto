package co.edu.uniquindio.proyecto.application.dto.request;

import jakarta.validation.constraints.*;

/**
 * DTO para cerrar una solicitud
 * Mapea a: Solicitud.cerrar()
 *
 * El motivo de cierre es obligatorio
 */
public record CerrarSolicitudRequest(

        @NotBlank(message = "El motivo de cierre es obligatorio")
        @Size(min = 10, max = 500,
                message = "El motivo debe tener entre 10 y 500 caracteres")
        String motivoCierre
) {}
