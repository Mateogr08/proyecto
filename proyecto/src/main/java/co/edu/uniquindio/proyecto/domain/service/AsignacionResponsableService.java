package co.edu.uniquindio.proyecto.domain.service;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;

import java.util.List;

public class AsignacionResponsableService {

    /**
     * Asigna un responsable a la solicitud según reglas de carga de trabajo.
     */
    public void asignarResponsable(Solicitud solicitud, List<Usuario> responsables) {
        // Ejemplo simple: asigna el primero disponible
        if(responsables.isEmpty()) {
            throw new IllegalArgumentException("No hay responsables disponibles");
        }
        Usuario asignado = responsables.get(0);
        solicitud.setResponsable(asignado);
    }
}