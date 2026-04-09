package co.edu.uniquindio.proyecto.application.dto.request;

import jakarta.validation.constraints.*;

/**
 * DTO para clasificar una solicitud
 * Mapea a: Solicitud.clasificar()
 *
 * Solo se permite clasificar solicitudes en estado REGISTRADA
 * La clasificación define la prioridad de atención
 */
public record ClasificarSolicitudRequest(

        @NotNull(message = "La prioridad es obligatoria")
        Long prioridadId
) {}