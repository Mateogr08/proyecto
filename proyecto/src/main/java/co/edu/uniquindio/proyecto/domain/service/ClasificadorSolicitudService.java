package co.edu.uniquindio.proyecto.domain.service;

import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoSolicitud;

public class ClasificadorSolicitudService {

    public Prioridad calcularPrioridadSugerida(String descripcion, TipoSolicitud tipo) {
        // Regla: Si el tipo es 'Cancelación de asignaturas' y falta 1 día para la fecha límite,
        // la prioridad debe ser ALTA con justificación de impacto académico.
        if (tipo.nombre().equals("Cancelación") && descripcion.contains("urgente")) {
            return new Prioridad("ALTA", "Sugerido por proximidad a fecha límite y términos críticos.");
        }
        return new Prioridad("MEDIA", "Asignada por defecto según tipo de trámite.");
    }
}
