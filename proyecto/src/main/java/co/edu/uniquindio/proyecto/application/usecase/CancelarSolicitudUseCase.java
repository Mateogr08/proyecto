package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.repository.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.service.GestionSolicitudService;

/**
 * Caso de uso para cancelar una solicitud.
 */
public class CancelarSolicitudUseCase {

    private final SolicitudRepository solicitudRepository;
    private final UsuarioRepository usuarioRepository;
    private final GestionSolicitudService service;

    public CancelarSolicitudUseCase(SolicitudRepository solicitudRepository, UsuarioRepository usuarioRepository, GestionSolicitudService service) {
        this.solicitudRepository = solicitudRepository;
        this.usuarioRepository = usuarioRepository;
        this.service = service;
    }

    public void ejecutar(String idSolicitud, String motivo, String idActor) {

        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        Usuario actor = usuarioRepository.findById(idActor)
                .orElseThrow(() -> new IllegalArgumentException("Actor no encontrado"));

        service.cancelarSolicitud(solicitud, motivo, actor);

        solicitudRepository.save(solicitud);
    }
}