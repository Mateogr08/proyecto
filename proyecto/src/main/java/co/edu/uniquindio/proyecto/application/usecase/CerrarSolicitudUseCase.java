package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.CodigoSolicitud;

public class CerrarSolicitudUseCase {

    private final SolicitudRepository repository;

    public CerrarSolicitudUseCase(SolicitudRepository repository) {
        this.repository = repository;
    }

    public void ejecutar(CodigoSolicitud id, String observacion, Usuario actor) {
        Solicitud solicitud = repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        solicitud.cerrar(observacion, actor);

        repository.guardar(solicitud);
    }
}