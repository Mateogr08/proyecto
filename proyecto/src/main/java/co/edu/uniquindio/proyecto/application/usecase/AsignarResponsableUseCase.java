package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.CodigoSolicitud;

public class AsignarResponsableUseCase {

    private final SolicitudRepository repository;

    public AsignarResponsableUseCase(SolicitudRepository repository) {
        this.repository = repository;
    }

    public void ejecutar(CodigoSolicitud id, Usuario responsable, Usuario actor) {
        Solicitud solicitud = repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        solicitud.asignarResponsable(responsable, actor);

        repository.guardar(solicitud);
    }
}