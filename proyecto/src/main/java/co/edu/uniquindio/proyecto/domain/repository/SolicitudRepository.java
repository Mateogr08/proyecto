package co.edu.uniquindio.proyecto.domain.repository;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.EstadoSolicitud;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio del agregado Solicitud.
 *
 * <p>Define las operaciones de persistencia y consulta del dominio
 * sin contener lógica de negocio.</p>
 */
public interface SolicitudRepository {

    /**
     * Busca una solicitud por su identificador único.
     *
     * @param id identificador de la solicitud
     * @return Optional con la solicitud si existe
     */
    Optional<Solicitud> findById(String id);

    /**
     * Guarda o actualiza una solicitud.
     *
     * @param solicitud entidad a persistir
     */
    Solicitud save(Solicitud solicitud);

    /**
     * Lista todas las solicitudes del sistema con paginación.
     *
     * @param offset desplazamiento inicial
     * @param limit cantidad máxima de resultados
     * @return lista de solicitudes
     */
    List<Solicitud> findAll(int offset, int limit);

    /**
     * Lista solicitudes por estado con paginación.
     *
     * @param estado estado de la solicitud
     * @param offset desplazamiento inicial
     * @param limit cantidad máxima de resultados
     * @return lista de solicitudes filtradas
     */
    List<Solicitud> findByEstado(EstadoSolicitud estado, int offset, int limit);

    /**
     * Lista solicitudes aplicando filtros opcionales con paginación.
     *
     * @param estado estado de la solicitud (opcional)
     * @param tipo tipo de solicitud (opcional)
     * @param idSolicitante id del solicitante (opcional)
     * @param idResponsable id del responsable (opcional)
     * @param offset desplazamiento inicial
     * @param limit cantidad máxima de resultados
     * @return lista de solicitudes filtradas
     */
    List<Solicitud> findByFiltros(EstadoSolicitud estado, String tipo, String idSolicitante, String idResponsable, int offset, int limit);

    /**
     * Busca solicitudes por identificador del solicitante.
     *
     * @param idSolicitante identificador del usuario solicitante
     * @return lista de solicitudes del usuario
     */
    List<Solicitud> findBySolicitanteIdentificacion(String idSolicitante);
}