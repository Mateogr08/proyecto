package co.edu.uniquindio.proyecto.infrastructure;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.repository.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.CodigoSolicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.EstadoSolicitud;

import java.util.*;
import java.util.stream.Collectors;

public class SolicitudRepositoryInMemory implements SolicitudRepository {

    private final Map<CodigoSolicitud, Solicitud> data = new HashMap<>();

    @Override
    public void guardar(Solicitud solicitud) {
        data.put(solicitudId(solicitud), solicitud);
    }

    @Override
    public Optional<Solicitud> buscarPorId(CodigoSolicitud id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public List<Solicitud> buscarPorEstado(EstadoSolicitud estado) {
        return data.values().stream()
                .filter(s -> s.getEstado().equals(estado))
                .collect(Collectors.toList());
    }

    private CodigoSolicitud solicitudId(Solicitud solicitud) {
        try {
            var field = Solicitud.class.getDeclaredField("id");
            field.setAccessible(true);
            return (CodigoSolicitud) field.get(solicitud);
        } catch (Exception e) {
            throw new RuntimeException("Error accediendo al ID de Solicitud");
        }
    }
}