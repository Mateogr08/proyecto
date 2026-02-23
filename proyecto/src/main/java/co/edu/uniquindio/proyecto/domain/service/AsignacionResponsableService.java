package co.edu.uniquindio.proyecto.domain.service;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.DomainException;

public class AsignacionResponsableService {

    public void asignarTramite(Solicitud solicitud, Usuario responsable) {
        // Regla: Validaciones cruzadas antes de ejecutar la acción en el agregado
        if (!responsable.isActivo()) {
            throw new DomainException("El responsable no puede recibir solicitudes porque está inactivo.");
        }
        solicitud.asignarResponsable(responsable);
    }
}