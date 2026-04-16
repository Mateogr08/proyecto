package co.edu.uniquindio.proyecto.domain.repository;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.CodigoSolicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.EstadoSolicitud;

import java.util.List;
import java.util.Optional;


public interface SolicitudRepository {
    Solicitud buscarPorId(String id);
    void guardar(Solicitud solicitud);
}