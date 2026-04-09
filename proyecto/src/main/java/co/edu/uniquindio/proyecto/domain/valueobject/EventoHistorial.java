package co.edu.uniquindio.proyecto.domain.valueobject;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;

import java.time.LocalDateTime;

/**
 * Representa un evento registrado en el historial de una solicitud.
 *
 * <p>Cada evento almacena información sobre una acción o cambio ocurrido
 * durante el ciclo de vida de una solicitud, incluyendo la fecha en que
 * ocurrió, una descripción del evento, el estado de la solicitud
 * relacionado con dicho evento y el usuario que ejecutó la acción.</p>
 *
 * <p>Estos eventos permiten mantener trazabilidad completa de todas las acciones
 * realizadas sobre una solicitud a lo largo de su gestión.</p>
 *
 * @param fecha momento en que ocurrió el evento
 * @param descripcion descripción del evento registrado
 * @param estadoRelacionado estado de la solicitud asociado al evento
 * @param actor usuario que ejecutó la acción
 */
public record EventoHistorial(
        LocalDateTime fecha,
        String descripcion,
        EstadoSolicitud estadoRelacionado,
        Usuario actor
) {

    /**
     * Constructor compacto del record que valida los datos del evento.
     *
     * <p>Si la fecha no es proporcionada, se asigna automáticamente
     * la fecha y hora actual. Además, se valida que la descripción
     * del evento no sea nula ni vacía, el estado y el actor sean válidos.</p>
     *
     * @throws IllegalArgumentException si los datos son inválidos
     */
    public EventoHistorial {

        if (fecha == null) {
            fecha = LocalDateTime.now();
        }

        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción del evento es obligatoria");
        }

        if (estadoRelacionado == null) {
            throw new IllegalArgumentException("El estado relacionado es obligatorio");
        }

        if (actor == null) {
            throw new IllegalArgumentException("El actor del evento es obligatorio");
        }
    }
}