package co.edu.uniquindio.proyecto.application.dto.response;

/**
 * DTO de resumen de una solicitud.
 *
 * <p>Se utiliza en listados o vistas generales donde no se requiere
 * toda la información detallada.</p>
 */
public record SolicitudResumenResponse(

        String id,

        String tipo,

        String estado,

        String prioridad

) {}
