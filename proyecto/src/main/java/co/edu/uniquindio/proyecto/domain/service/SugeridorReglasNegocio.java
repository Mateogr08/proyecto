package co.edu.uniquindio.proyecto.domain.service;

import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoSolicitud;

/**
 * Implementación de {@link SugeridorPrioridad} basada en reglas de negocio.
 *
 * <p>Este componente determina la prioridad de una solicitud utilizando
 * condiciones predefinidas del dominio. Analiza la descripción y el tipo
 * de solicitud para asignar un nivel de prioridad adecuado.</p>
 *
 * <p>Por ejemplo, si la descripción contiene la palabra "urgente" y el tipo
 * de solicitud corresponde a una cancelación, se asigna una prioridad alta.</p>
 *
 * <p>Si no se cumple ninguna regla específica, la prioridad sugerida se
 * determina automáticamente según el tipo de solicitud.</p>
 */

public class SugeridorReglasNegocio implements SugeridorPrioridad {

    /**
     * Sugiere la prioridad de una solicitud basándose en reglas
     * de negocio definidas en el sistema.
     *
     * @param descripcion descripción de la solicitud
     * @param tipo tipo de solicitud académica
     * @return prioridad sugerida según las reglas del negocio
     */
    @Override
    public Prioridad sugerir(String descripcion, TipoSolicitud tipo) {
        if (descripcion.toLowerCase().contains("urgente") && tipo == TipoSolicitud.CANCELACION) {
            return new Prioridad("ALTA", "Prioridad aumentada por detección de urgencia en trámite crítico.");
        }

        return new Prioridad(
                tipo.getPrioridadSugerida(),
                "Asignada automáticamente por tipo de trámite: " + tipo.getNombre()
        );
    }
}