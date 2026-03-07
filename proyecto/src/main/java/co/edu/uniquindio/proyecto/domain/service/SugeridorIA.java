package co.edu.uniquindio.proyecto.domain.service;

import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoSolicitud;

public class SugeridorIA implements SugeridorPrioridad {
    @Override
    public Prioridad sugerir(String descripcion, TipoSolicitud tipo) {
        // Aquí iría la lógica de conexión con OpenAI/Gemini/etc.
        return new Prioridad("ALTA", "Sugerido por análisis de lenguaje natural (IA) basado en: " + descripcion);
    }
}
