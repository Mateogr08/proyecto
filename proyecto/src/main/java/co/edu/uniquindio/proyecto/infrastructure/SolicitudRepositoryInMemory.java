package co.edu.uniquindio.proyecto.infrastructure;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.repository.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.EstadoSolicitud;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class SolicitudRepositoryInMemory implements SolicitudRepository {

    private final List<Solicitud> solicitudes = new ArrayList<>();

    @Override
    public Optional<Solicitud> findById(String id) {
        return solicitudes.stream()
                .filter(s -> s.getId().getValor().equals(id))
                .findFirst();
    }

    @Override
    public Solicitud save(Solicitud solicitud) {
        solicitudes.removeIf(s -> s.getId().getValor().equals(solicitud.getId().getValor()));
        solicitudes.add(solicitud);
        return solicitud;
    }

    @Override
    public List<Solicitud> findAll(int offset, int limit) {
        return solicitudes.stream()
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public List<Solicitud> findByEstado(EstadoSolicitud estado, int offset, int limit) {
        return solicitudes.stream()
                .filter(s -> s.getEstado() == estado)
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public List<Solicitud> findByFiltros(EstadoSolicitud estado, String tipo, String idSolicitante, String idResponsable, int offset, int limit) {
        return solicitudes.stream()
                .filter(s -> estado == null || s.getEstado() == estado)
                .filter(s -> tipo == null || s.getTipo().name().equalsIgnoreCase(tipo))
                .filter(s -> idSolicitante == null || s.getSolicitante().getIdentificacion().equals(idSolicitante))
                .filter(s -> idResponsable == null || (s.getResponsable() != null && s.getResponsable().getIdentificacion().equals(idResponsable)))
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public List<Solicitud> findBySolicitanteIdentificacion(String idSolicitante) {
        return solicitudes.stream()
                .filter(s -> s.getSolicitante().getIdentificacion().equals(idSolicitante))
                .collect(Collectors.toList());
    }
}
