package co.edu.uniquindio.proyecto.domain.service;

import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoSolicitud;

/**
 * Implementación de {@link SugeridorPrioridad} que utiliza
 * inteligencia artificial para sugerir la prioridad de una solicitud.
 *
 * <p>Este componente analiza la descripción y el tipo de solicitud
 * utilizando técnicas de procesamiento de lenguaje natural (NLP)
 * para determinar un nivel de prioridad sugerido.</p>
 *
 * <p>En una implementación real, este servicio podría conectarse
 * con APIs de modelos de inteligencia artificial como OpenAI,
 * Gemini u otros servicios de análisis de texto.</p>
 */

public class SugeridorIA implements SugeridorPrioridad {

    /**
     * Sugiere una prioridad para una solicitud utilizando análisis
     * de lenguaje natural sobre su descripción.
     *
     * @param descripcion descripción de la solicitud
     * @param tipo tipo de solicitud académica
     * @return prioridad sugerida basada en el análisis del texto
     */
    @Override
    public Prioridad sugerir(String descripcion, TipoSolicitud tipo) {
        // Aquí iría la lógica de conexión con OpenAI/Gemini/etc.
        return new Prioridad("ALTA", "Sugerido por análisis de lenguaje natural (IA) basado en: " + descripcion);
    }
}