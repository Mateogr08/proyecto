package co.edu.uniquindio.proyecto.domain.service;

import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoSolicitud;

/**
 * Servicio de dominio encargado de determinar la prioridad sugerida
 * para una solicitud.
 *
 * <p>Este servicio utiliza una estrategia de sugerencia representada
 * por la interfaz {@link SugeridorPrioridad}, lo que permite aplicar
 * diferentes mecanismos de cálculo de prioridad, como reglas de negocio
 * o análisis mediante inteligencia artificial.</p>
 */

public class ClasificadorSolicitudService {

    private final SugeridorPrioridad sugeridor;

    /**
     * Crea un nuevo clasificador de solicitudes utilizando un
     * sugeridor de prioridad específico.
     *
     * @param sugeridor componente encargado de sugerir la prioridad
     * de una solicitud
     */
    public ClasificadorSolicitudService(SugeridorPrioridad sugeridor) {
        this.sugeridor = sugeridor;
    }

    /**
     * Calcula la prioridad sugerida para una solicitud basándose
     * en su descripción y tipo.
     *
     * <p>La lógica de cálculo se delega al componente
     * {@link SugeridorPrioridad}, lo que permite cambiar la
     * estrategia de sugerencia sin modificar este servicio.</p>
     *
     * @param descripcion descripción de la solicitud
     * @param tipo tipo de solicitud académica
     * @return prioridad sugerida para la solicitud
     */
    public Prioridad calcularPrioridadSugerida(String descripcion, TipoSolicitud tipo) {
        return sugeridor.sugerir(descripcion, tipo);
    }
}