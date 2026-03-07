package co.edu.uniquindio.proyecto.domain.service;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.DomainException;

/**
 * Servicio de dominio encargado de gestionar la asignación de
 * responsables a una solicitud.
 *
 * <p>Este servicio contiene reglas de negocio que deben validarse
 * antes de delegar la operación al agregado {@link Solicitud}.
 * En particular, verifica que el usuario responsable esté activo
 * antes de permitir la asignación.</p>
 */

public class AsignacionResponsableService {

    /**
     * Asigna un responsable para atender una solicitud.
     *
     * <p>Antes de realizar la asignación se valida que el usuario
     * responsable esté activo. Si la validación es correcta, la
     * operación se delega a la entidad {@link Solicitud}.</p>
     *
     * @param solicitud solicitud a la cual se le asignará el responsable
     * @param responsable usuario encargado de atender la solicitud
     *
     * @throws DomainException si el responsable se encuentra inactivo
     */
    public void asignarTramite(Solicitud solicitud, Usuario responsable) {
        if (!responsable.isActivo()) {
            throw new DomainException("El responsable no puede recibir solicitudes porque está inactivo.");
        }
        solicitud.asignarResponsable(responsable);
    }
}