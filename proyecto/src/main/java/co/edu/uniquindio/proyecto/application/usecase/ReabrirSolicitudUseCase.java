package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.domain.entity.*;
import co.edu.uniquindio.proyecto.domain.repository.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.service.GestionSolicitudService;

public class ReabrirSolicitudUseCase {

    private final SolicitudRepository repository;
    private final GestionSolicitudService service;

    public ReabrirSolicitudUseCase(SolicitudRepository repository,
                                   GestionSolicitudService service) {
        this.repository = repository;
        this.service = service;
    }

    /**
     * Reabre una solicitud previamente cerrada.
     */
    public void ejecutar(String idSolicitud, String motivo, Usuario actor) {

        Solicitud solicitud = repository.buscarPorId(idSolicitud);

        service.reabrirSolicitud(solicitud, motivo, actor);

        repository.guardar(solicitud);
    }
}