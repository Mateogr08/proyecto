package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.repository.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.service.AsignacionResponsableService;

/**
 * Caso de uso para asignar un responsable a una solicitud.
 */
public class AsignarResponsableUseCase {

    private final SolicitudRepository solicitudRepository;
    private final UsuarioRepository usuarioRepository;
    private final AsignacionResponsableService service;

    public AsignarResponsableUseCase(SolicitudRepository solicitudRepository, UsuarioRepository usuarioRepository, AsignacionResponsableService service) {
        this.solicitudRepository = solicitudRepository;
        this.usuarioRepository = usuarioRepository;
        this.service = service;
    }

    public void ejecutar(String idSolicitud, String idResponsable, String idActor) {

        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        Usuario responsable = usuarioRepository.findById(idResponsable)
                .orElseThrow(() -> new IllegalArgumentException("Responsable no encontrado"));

        Usuario actor = usuarioRepository.findById(idActor)
                .orElseThrow(() -> new IllegalArgumentException("Actor no encontrado"));

        service.asignarResponsable(solicitud, responsable, actor);

        solicitudRepository.save(solicitud);
    }
}