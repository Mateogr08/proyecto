package co.edu.uniquindio.proyecto.domain.service;

import co.edu.uniquindio.proyecto.domain.entity.*;

import org.springframework.stereotype.Service;

/**
 * Servicio de dominio encargado de la gestión del ciclo de vida
 * de una solicitud.
 */
@Service
public class GestionSolicitudService {

    public void cerrarSolicitud(Solicitud solicitud, String observacion, Usuario actor) {

        if (!actor.esAdministrador()) {
            throw new IllegalStateException("Solo un administrador puede cerrar solicitudes");
        }

        solicitud.cerrar(observacion, actor);
    }

    public void cancelarSolicitud(Solicitud solicitud, String motivo, Usuario actor) {

        boolean autorizado = actor.esAdministrador() || actor.equals(solicitud.getSolicitante());

        if (!autorizado) {
            throw new IllegalStateException("No autorizado para cancelar la solicitud");
        }

        solicitud.cancelar(motivo, actor);
    }

    public void rechazarSolicitud(Solicitud solicitud, String motivo, Usuario actor) {

        if (!actor.esAdministrador()) {
            throw new IllegalStateException("Solo un administrador puede rechazar solicitudes");
        }

        solicitud.rechazar(motivo, actor);
    }

    public void reabrirSolicitud(Solicitud solicitud, String motivo, Usuario actor) {

        if (!actor.esAdministrador()) {
            throw new IllegalStateException("Solo un administrador puede reabrir solicitudes");
        }

        solicitud.reabrir(motivo, actor);
    }
}