package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.repository.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.service.GestionSolicitudService;

public class RechazarSolicitudUseCase {

    private final SolicitudRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final GestionSolicitudService service;

    public RechazarSolicitudUseCase(SolicitudRepository repository, UsuarioRepository usuarioRepository, GestionSolicitudService service) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.service = service;
    }

    public void ejecutar(String idSolicitud, String motivo, String idActor) {
        Solicitud solicitud = repository.findById(idSolicitud)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        Usuario actor = usuarioRepository.findById(idActor)
                .orElseThrow(() -> new IllegalArgumentException("Actor no encontrado"));

        service.rechazarSolicitud(solicitud, motivo, actor);
        repository.save(solicitud);
    }
}