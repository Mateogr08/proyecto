package co.edu.uniquindio.proyecto.application.dto.request;

import jakarta.validation.constraints.*;

/**
 * DTO para asignar un responsable a la solicitud
 * Mapea a: Solicitud.asignarResponsable()
 *
 * Solo se puede asignar si la solicitud está clasificada
 */
public record AsignarResponsableRequest(

        @NotBlank(message = "El email del responsable es obligatorio")
        @Email(message = "Debe ser un email válido")
        String emailResponsable
) {}
