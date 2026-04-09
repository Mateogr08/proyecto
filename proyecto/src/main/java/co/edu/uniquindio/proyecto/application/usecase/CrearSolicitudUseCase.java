package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.repository.SolicitudRepository;

public class CrearSolicitudUseCase {

    private final SolicitudRepository repository;

    public CrearSolicitudUseCase(SolicitudRepository repository) {
        this.repository = repository;
    }

    public void ejecutar(Solicitud solicitud) {
        repository.guardar(solicitud);
    }
}