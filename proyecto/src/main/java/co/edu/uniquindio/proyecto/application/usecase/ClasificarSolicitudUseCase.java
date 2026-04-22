package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.repository.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.service.ClasificadorSolicitudService;
import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;

/**
 * Clasifica una solicitud asignando prioridad.
 */
public class ClasificarSolicitudUseCase {

    private final SolicitudRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final ClasificadorSolicitudService service;

    public ClasificarSolicitudUseCase(SolicitudRepository repository, UsuarioRepository usuarioRepository, ClasificadorSolicitudService service) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.service = service;
    }

    public void ejecutar(String idSolicitud, Prioridad prioridad, String idActor) {

        Solicitud solicitud = repository.findById(idSolicitud)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        Usuario actor = usuarioRepository.findById(idActor)
                .orElseThrow(() -> new IllegalArgumentException("Actor no encontrado"));

        service.clasificarSolicitud(solicitud, prioridad, actor);

        repository.save(solicitud);
    }
}
