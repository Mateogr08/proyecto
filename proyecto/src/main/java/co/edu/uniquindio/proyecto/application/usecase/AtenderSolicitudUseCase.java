package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.repository.UsuarioRepository;

/**
 * Marca una solicitud como atendida.
 */
public class AtenderSolicitudUseCase {

    private final SolicitudRepository repository;
    private final UsuarioRepository usuarioRepository;

    public AtenderSolicitudUseCase(SolicitudRepository repository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    public void ejecutar(String idSolicitud, String idActor) {

        Solicitud solicitud = repository.findById(idSolicitud)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        Usuario actor = usuarioRepository.findById(idActor)
                .orElseThrow(() -> new IllegalArgumentException("Actor no encontrado"));

        solicitud.marcarComoAtendida(actor);

        repository.save(solicitud);
    }
}