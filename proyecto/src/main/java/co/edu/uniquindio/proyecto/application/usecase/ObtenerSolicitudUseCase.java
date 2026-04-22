package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.repository.SolicitudRepository;

/**
 * Obtiene una solicitud por ID.
 */
public class ObtenerSolicitudUseCase {

    private final SolicitudRepository repository;

    public ObtenerSolicitudUseCase(SolicitudRepository repository) {
        this.repository = repository;
    }

    public Solicitud ejecutar(String idSolicitud) {

        return repository.findById(idSolicitud)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));
    }
}