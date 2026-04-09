package co.edu.uniquindio.proyecto.application.dto.response;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO detallado de una solicitud
 * Mapea a: Solicitud
 *
 * Incluye solo atributos relevantes para consulta de detalle
 */
public record SolicitudDetalleResponse(

        String id,                     // ID de la solicitud
        String tipoSolicitud,           // Nombre del tipo
        String prioridad,               // Prioridad en texto
        String estado,                  // Estado actual
        String descripcion,             // Descripción del solicitante
        String emailSolicitante,        // Solicitante
        String emailResponsable,        // Responsable asignado (si existe)
        LocalDateTime fechaCreacion,    // Fecha de creación
        LocalDateTime fechaCierre,      // Fecha de cierre (si existe)
        List<EventoHistorialResponse> historial // Historial de eventos
) {}