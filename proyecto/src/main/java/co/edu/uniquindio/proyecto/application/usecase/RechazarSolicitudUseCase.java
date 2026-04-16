package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.domain.entity.*;
import co.edu.uniquindio.proyecto.domain.repository.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.service.GestionSolicitudService;

public class RechazarSolicitudUseCase {

    private final SolicitudRepository repository;
    private final GestionSolicitudService service;

    public RechazarSolicitudUseCase(SolicitudRepository repository,
                                    GestionSolicitudService service) {
        this.repository = repository;
        this.service = service;
    }

    /**
     * Rechaza una solicitud clasificada.
     */
    public void ejecutar(String idSolicitud, String motivo, Usuario actor) {

        Solicitud solicitud = repository.buscarPorId(idSolicitud);

        service.rechazarSolicitud(solicitud, motivo, actor);

        repository.guardar(solicitud);
    }
}