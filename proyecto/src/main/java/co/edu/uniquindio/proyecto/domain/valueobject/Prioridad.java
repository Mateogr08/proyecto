package co.edu.uniquindio.proyecto.domain.valueobject;

/**
 * Representa la prioridad asignada a una solicitud dentro del sistema.
 *
 * <p>Este objeto de valor encapsula el nivel de prioridad de una solicitud
 * junto con una justificación que explica por qué se asignó dicha prioridad.
 * La justificación permite mantener trazabilidad y transparencia en el
 * proceso de clasificación.</p>
 *
 * @param nivel nivel de prioridad de la solicitud (por ejemplo: ALTA, MEDIA o BAJA)
 * @param justificacion explicación del motivo por el cual se asignó la prioridad
 */

public record Prioridad(String nivel, String justificacion) {

    /**
     * Constructor compacto del record que valida los datos de la prioridad.
     *
     * <p>Se verifica que el nivel no sea nulo ni vacío y que la justificación
     * tenga una longitud mínima para asegurar que la explicación sea clara
     * y suficiente.</p>
     *
     * @throws IllegalArgumentException si el nivel es nulo o vacío,
     * o si la justificación tiene menos de 10 caracteres
     */
    public Prioridad {
        if (nivel == null || nivel.isBlank()) {
            throw new IllegalArgumentException("El nivel es obligatorio");
        }
        if (justificacion == null || justificacion.length() < 10) {
            throw new IllegalArgumentException("La justificación de la prioridad debe ser detallada (mínimo 10 caracteres)");
        }
    }
}