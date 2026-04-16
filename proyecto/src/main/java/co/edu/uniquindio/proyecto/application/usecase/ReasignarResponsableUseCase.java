package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.domain.entity.*;
import co.edu.uniquindio.proyecto.domain.repository.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.service.AsignacionResponsableService;

public class ReasignarResponsableUseCase {

    private final SolicitudRepository repository;
    private final AsignacionResponsableService service;

    public ReasignarResponsableUseCase(SolicitudRepository repository,
                                       AsignacionResponsableService service) {
        this.repository = repository;
        this.service = service;
    }

    /**
     * Permite reasignar el responsable de una solicitud.
     */
    public void ejecutar(String idSolicitud, Usuario nuevo, Usuario actor) {

        Solicitud solicitud = repository.buscarPorId(idSolicitud);

        service.reasignarResponsable(solicitud, nuevo, actor);

        repository.guardar(solicitud);
    }
}