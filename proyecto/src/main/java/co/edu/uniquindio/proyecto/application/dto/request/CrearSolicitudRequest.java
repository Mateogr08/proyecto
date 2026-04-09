package co.edu.uniquindio.proyecto.application.dto.request;

import jakarta.validation.constraints.*;

/**
 * DTO para crear una nueva solicitud
 * Mapea a: Solicitud.crear()
 *
 * NO incluir:
 * - estado
 * - fechaCreacion
 * - id
 * - titulo
 *
 * Estos los gestiona el dominio automáticamente
 */
public record CrearSolicitudRequest(

        @NotBlank(message = "La descripción es obligatoria")
        @Size(min = 20, max = 1000,
                message = "La descripción debe tener entre 20 y 1000 caracteres")
        String descripcion,

        @NotNull(message = "El tipo de solicitud es obligatorio")
        Long tipoSolicitudId,

        @NotNull(message = "La prioridad es obligatoria")
        Long prioridadId,

        @NotBlank(message = "El email del solicitante es obligatorio")
        @Email(message = "Debe ser un email válido")
        String emailSolicitante
) {}