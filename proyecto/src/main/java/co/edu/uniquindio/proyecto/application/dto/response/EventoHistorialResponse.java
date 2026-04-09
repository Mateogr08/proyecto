package co.edu.uniquindio.proyecto.application.dto.response;

import java.time.LocalDateTime;

/**
 * DTO que representa un evento del historial de la solicitud
 * Mapea a: EventoHistorial
 */
public record EventoHistorialResponse(

        String descripcion,
        String estadoAnterior,
        String estadoNuevo,
        String responsable,
        String observacion,
        LocalDateTime fecha
) {}
