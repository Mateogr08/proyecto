package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.domain.entity.*;
import co.edu.uniquindio.proyecto.domain.repository.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.service.ClasificadorSolicitudService;
import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;

public class ClasificarSolicitudUseCase {

    private final SolicitudRepository repository;
    private final ClasificadorSolicitudService service;

    public ClasificarSolicitudUseCase(SolicitudRepository repository,
                                      ClasificadorSolicitudService service) {
        this.repository = repository;
        this.service = service;
    }

    /**
     * Clasifica una solicitud asignándole una prioridad.
     *
     * @param idSolicitud identificador de la solicitud
     * @param prioridad prioridad a asignar
     * @param actor usuario que ejecuta la acción
     */
    public void ejecutar(String idSolicitud, Prioridad prioridad, Usuario actor) {

        Solicitud solicitud = repository.buscarPorId(idSolicitud);

        service.clasificarSolicitud(solicitud, prioridad, actor);

        repository.guardar(solicitud);
    }
}