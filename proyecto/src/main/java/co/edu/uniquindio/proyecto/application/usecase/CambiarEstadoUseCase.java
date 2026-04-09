package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.CodigoSolicitud;

public class CambiarEstadoUseCase {

    private final SolicitudRepository repository;

    public CambiarEstadoUseCase(SolicitudRepository repository) {
        this.repository = repository;
    }

    public void marcarComoAtendida(CodigoSolicitud id, Usuario actor) {
        Solicitud solicitud = repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        solicitud.marcarComoAtendida(actor);

        repository.guardar(solicitud);
    }
}