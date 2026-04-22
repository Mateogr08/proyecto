package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.repository.SolicitudRepository;

public class ObtenerHistorialSolicitudUseCase {

    private final SolicitudRepository repository;

    public ObtenerHistorialSolicitudUseCase(SolicitudRepository repository) {
        this.repository = repository;
    }

    public Solicitud ejecutar(String idSolicitud) {
        return repository.findById(idSolicitud)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));
    }
}