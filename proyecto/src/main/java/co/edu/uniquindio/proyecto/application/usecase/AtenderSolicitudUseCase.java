package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.repository.SolicitudRepository;

public class AtenderSolicitudUseCase {

    private final SolicitudRepository repository;

    public AtenderSolicitudUseCase(SolicitudRepository repository) {
        this.repository = repository;
    }

    /**
     * Marca una solicitud como atendida.
     *
     * <p>La solicitud debe estar en estado EN_ATENCION.</p>
     *
     * @param idSolicitud identificador de la solicitud
     */
    public void ejecutar(String idSolicitud) {

        Solicitud solicitud = repository.buscarPorId(idSolicitud);

        solicitud.marcarComoAtendida();

        repository.guardar(solicitud);
    }
}