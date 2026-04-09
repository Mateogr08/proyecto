package co.edu.uniquindio.proyecto.domain.service;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoSolicitud;

/**
 * Servicio que clasifica solicitudes automáticamente según
 * palabras clave en la descripción de la solicitud.
 */
public class ClasificacionSolicitudService {

    /**
     * Clasifica la solicitud según su descripción.
     *
     * @param solicitud solicitud a clasificar
     * @return tipo de solicitud detectado
     * @throws IllegalArgumentException si no se puede determinar el tipo
     */
    public TipoSolicitud clasificar(Solicitud solicitud) {
        if (solicitud == null)
            throw new IllegalArgumentException("La solicitud no puede ser null");

        String desc = solicitud.getDescripcion();
        if (desc == null || desc.isBlank())
            throw new IllegalArgumentException("La descripción de la solicitud es obligatoria");

        desc = desc.toLowerCase();

        // Palabras clave para determinar el tipo
        if (desc.contains("homologar") || desc.contains("homologación"))
            return TipoSolicitud.HOMOLOGACION;
        if (desc.contains("registro") || desc.contains("matrícula"))
            return TipoSolicitud.REGISTRO_ASIGNATURAS;
        if (desc.contains("cancelar") || desc.contains("cancelación"))
            return TipoSolicitud.CANCELACION;
        if (desc.contains("cupo"))
            return TipoSolicitud.CUPO;
        if (desc.contains("consulta"))
            return TipoSolicitud.CONSULTA;

        // Si no coincide con ningún tipo conocido, lanza excepción clara
        throw new IllegalArgumentException(
                "No se pudo clasificar la solicitud: '" + solicitud.getDescripcion() +
                        "'. Por favor revise las palabras clave"
        );
    }
}