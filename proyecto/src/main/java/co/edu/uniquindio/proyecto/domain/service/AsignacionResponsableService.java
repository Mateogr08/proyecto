package co.edu.uniquindio.proyecto.domain.service;

import co.edu.uniquindio.proyecto.domain.entity.*;

import org.springframework.stereotype.Service;

/**
 * Servicio de dominio encargado de la asignación y reasignación
 * de responsables en solicitudes.
 */
@Service
public class AsignacionResponsableService {

    public void asignarResponsable(Solicitud solicitud, Usuario responsable, Usuario actor) {

        if (!actor.esAdministrador()) {
            throw new IllegalStateException("Solo un administrador puede asignar responsables");
        }

        if (!(responsable.esProfesor() || responsable.esAdministrador())) {
            throw new IllegalStateException("El responsable debe ser profesor o administrador");
        }

        if (!responsable.estaActivo()) {
            throw new IllegalStateException("El responsable debe estar activo");
        }

        solicitud.asignarResponsable(responsable, actor);
    }

    public void reasignarResponsable(Solicitud solicitud, Usuario nuevoResponsable, Usuario actor) {

        if (!actor.esAdministrador()) {
            throw new IllegalStateException("Solo un administrador puede reasignar responsables");
        }

        if (!nuevoResponsable.estaActivo()) {
            throw new IllegalStateException("El nuevo responsable debe estar activo");
        }

        solicitud.reasignarResponsable(nuevoResponsable, actor);
    }
}