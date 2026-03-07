package co.edu.uniquindio.proyecto.domain.service;

import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoSolicitud;

public class ClasificadorSolicitudService {

    private final SugeridorPrioridad sugeridor;

    public ClasificadorSolicitudService(SugeridorPrioridad sugeridor) {
        this.sugeridor = sugeridor;
    }

    public Prioridad calcularPrioridadSugerida(String descripcion, TipoSolicitud tipo) {
        return sugeridor.sugerir(descripcion, tipo);
    }
}