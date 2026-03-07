package co.edu.uniquindio.proyecto.domain.service;

import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoSolicitud;

public class SugeridorReglasNegocio implements SugeridorPrioridad {

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