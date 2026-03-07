package co.edu.uniquindio.proyecto.domain.service;

import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoSolicitud;

/**
 * Define el contrato para los componentes encargados de sugerir
 * la prioridad de una solicitud.
 *
 * <p>Las implementaciones de esta interfaz pueden utilizar diferentes
 * estrategias para calcular la prioridad, como reglas de negocio
 * predefinidas o análisis mediante inteligencia artificial.</p>
 *
 * <p>Este diseño permite aplicar el principio de inversión de
 * dependencias y facilita cambiar la estrategia de sugerencia
 * sin modificar los servicios que la utilizan.</p>
 */

public interface SugeridorPrioridad {

    /**
     * Calcula o sugiere la prioridad de una solicitud a partir
     * de su descripción y tipo.
     *
     * @param descripcion descripción de la solicitud
     * @param tipo tipo de solicitud académica
     * @return prioridad sugerida para la solicitud
     */
    Prioridad sugerir(String descripcion, TipoSolicitud tipo);
}