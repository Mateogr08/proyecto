package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.repository.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.service.AsignacionResponsableService;

public class ReasignarResponsableUseCase {

    private final SolicitudRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final AsignacionResponsableService service;

    public ReasignarResponsableUseCase(SolicitudRepository repository, UsuarioRepository usuarioRepository, AsignacionResponsableService service) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.service = service;
    }

    public void ejecutar(String idSolicitud, String idNuevoResponsable, String idActor) {
        Solicitud solicitud = repository.findById(idSolicitud)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        Usuario nuevoResponsable = usuarioRepository.findById(idNuevoResponsable)
                .orElseThrow(() -> new IllegalArgumentException("Nuevo responsable no encontrado"));

        Usuario actor = usuarioRepository.findById(idActor)
                .orElseThrow(() -> new IllegalArgumentException("Actor no encontrado"));

        service.reasignarResponsable(solicitud, nuevoResponsable, actor);
        repository.save(solicitud);
    }
}