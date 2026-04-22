package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.repository.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.service.GeneradorCodigoService;
import co.edu.uniquindio.proyecto.domain.valueobject.*;

/**
 * Caso de uso encargado de crear una nueva solicitud académica.
 */
public class CrearSolicitudUseCase {

    private final SolicitudRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final GeneradorCodigoService generadorCodigo;

    public CrearSolicitudUseCase(SolicitudRepository repository, UsuarioRepository usuarioRepository, GeneradorCodigoService generadorCodigo) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.generadorCodigo = generadorCodigo;
    }

    public Solicitud ejecutar(String tipo,
                         String descripcion,
                         String canal,
                         String idSolicitante) {

        Usuario solicitante = usuarioRepository.findById(idSolicitante)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Solicitante no encontrado: " + idSolicitante
                ));

        Solicitud solicitud = new Solicitud(
                new CodigoSolicitud(generadorCodigo.generarCodigo()),
                TipoSolicitud.of(tipo),
                descripcion,
                CanalOrigen.of(canal),
                solicitante
        );

        return repository.save(solicitud);
    }
}
