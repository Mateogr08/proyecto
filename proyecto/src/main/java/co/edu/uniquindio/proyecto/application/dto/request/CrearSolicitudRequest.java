package co.edu.uniquindio.proyecto.application.dto.request;

import jakarta.validation.constraints.*;

/**
 * DTO para la creación de una solicitud.
 *
 * <p>Incluye validaciones de entrada para garantizar que los datos
 * enviados por el cliente cumplan con las reglas mínimas antes de
 * llegar al dominio.</p>
 */
public record CrearSolicitudRequest(

        @NotBlank(message = "El tipo de solicitud es obligatorio")
        String tipo,

        @NotBlank(message = "La descripción es obligatoria")
        @Size(min = 20, max = 1000,
                message = "La descripción debe tener entre 20 y 1000 caracteres")
        String descripcion,

        @NotBlank(message = "El canal es obligatorio")
        String canal,

        @NotBlank(message = "El ID del solicitante es obligatorio")
        String idSolicitante

) {}