package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.domain.entity.*;
import co.edu.uniquindio.proyecto.domain.repository.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.service.GestionSolicitudService;

public class CancelarSolicitudUseCase {

    private final SolicitudRepository repository;
    private final GestionSolicitudService service;

    public CancelarSolicitudUseCase(SolicitudRepository repository,
                                    GestionSolicitudService service) {
        this.repository = repository;
        this.service = service;
    }

    /**
     * Cancela una solicitud.
     *
     * @param idSolicitud id de la solicitud
     * @param motivo motivo de cancelación
     * @param actor usuario que ejecuta la acción
     */
    public void ejecutar(String idSolicitud, String motivo, Usuario actor) {

        Solicitud solicitud = repository.buscarPorId(idSolicitud);

        service.cancelarSolicitud(solicitud, motivo, actor);

        repository.guardar(solicitud);
    }
}
