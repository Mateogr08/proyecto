package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.*;

public class CrearSolicitudUseCase {

    private final SolicitudRepository repository;

    public CrearSolicitudUseCase(SolicitudRepository repository) {
        this.repository = repository;
    }

    /**
     * Crea una nueva solicitud en el sistema.
     *
     * <p>Este caso de uso permite a un estudiante registrar una solicitud
     * académica, la cual inicia en estado REGISTRADA.</p>
     *
     * @param id código de la solicitud
     * @param tipo tipo de solicitud
     * @param descripcion descripción del problema
     * @param canal canal de origen
     * @param solicitante usuario que crea la solicitud
     */
    public void ejecutar(CodigoSolicitud id,
                         TipoSolicitud tipo,
                         String descripcion,
                         CanalOrigen canal,
                         Usuario solicitante) {

        Solicitud solicitud = new Solicitud(
                id, tipo, descripcion, canal, solicitante
        );

        repository.guardar(solicitud);
    }
}