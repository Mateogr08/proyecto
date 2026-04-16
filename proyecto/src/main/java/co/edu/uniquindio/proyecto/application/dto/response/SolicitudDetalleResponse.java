package co.edu.uniquindio.proyecto.application.dto.response;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO detallado de una solicitud.
 *
 * <p>Contiene toda la información relevante del agregado Solicitud,
 * incluyendo historial, responsables y fechas clave.</p>
 */
public record SolicitudDetalleResponse(

        String id,

        String tipo,

        String descripcion,

        String estado,

        String prioridad,

        String canal,

        String solicitante,

        String responsable,

        LocalDateTime fechaCreacion,

        LocalDateTime fechaAtencion,

        LocalDateTime fechaCierre,

        List<EventoHistorialResponse> historial

) {}
