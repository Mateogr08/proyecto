package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.repository.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.EstadoSolicitud;

import java.util.List;

/**
 * Lista todas las solicitudes aplicando filtros opcionales.
 */
public class ListarSolicitudesUseCase {

    private final SolicitudRepository repository;

    public ListarSolicitudesUseCase(SolicitudRepository repository) {
        this.repository = repository;
    }

    public List<Solicitud> ejecutar(EstadoSolicitud estado, String tipo, String idSolicitante, String idResponsable, int page, int size) {
        int offset = page * size;
        
        // Si no hay filtros específicos, usamos findByFiltros que maneja la lógica dinámica
        return repository.findByFiltros(estado, tipo, idSolicitante, idResponsable, offset, size);
    }
}
