package co.edu.uniquindio.proyecto.application.dto.response;

import java.time.LocalDateTime;

/**
 * DTO que representa un evento dentro del historial de una solicitud.
 *
 * <p>Permite visualizar los cambios de estado y acciones realizadas
 * sobre la solicitud a lo largo del tiempo.</p>
 */
public record EventoHistorialResponse(

        LocalDateTime fecha,

        String descripcion,

        String estado,

        String actor

) {}
