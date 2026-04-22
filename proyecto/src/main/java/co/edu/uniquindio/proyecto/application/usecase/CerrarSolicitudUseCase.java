package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.RecursoNoEncontradoException;
import co.edu.uniquindio.proyecto.domain.repository.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.repository.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.service.GestionSolicitudService;

/**
 * Cierra una solicitud atendida.
 */
public class CerrarSolicitudUseCase {

    private final SolicitudRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final GestionSolicitudService service;

    public CerrarSolicitudUseCase(SolicitudRepository repository, UsuarioRepository usuarioRepository, GestionSolicitudService service) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.service = service;
    }

    public void ejecutar(String idSolicitud, String observacion, String idActor) {

        Solicitud solicitud = repository.findById(idSolicitud)
                .orElseThrow(() -> new RecursoNoEncontradoException("Solicitud no encontrada con ID: " + idSolicitud));

        Usuario actor = usuarioRepository.findById(idActor)
                .orElseThrow(() -> new RecursoNoEncontradoException("Actor no encontrado con ID: " + idActor));

        service.cerrarSolicitud(solicitud, observacion, actor);

        repository.save(solicitud);
    }
}
