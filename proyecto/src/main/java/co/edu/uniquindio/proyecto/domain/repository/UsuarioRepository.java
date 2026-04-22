package co.edu.uniquindio.proyecto.domain.repository;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida del dominio para la gestión de usuarios.
 *
 * <p>Define las operaciones necesarias para acceder y persistir
 * entidades {@link Usuario} sin acoplarse a infraestructura.</p>
 *
 * <p>Este repositorio es utilizado por los casos de uso para
 * consultar y validar usuarios dentro del sistema.</p>
 */
public interface UsuarioRepository {

    /**
     * Busca un usuario por su identificador único.
     *
     * @param id identificador del usuario
     * @return usuario si existe, vacío si no se encuentra
     */
    Optional<Usuario> findById(String id);

    /**
     * Lista todos los usuarios del sistema.
     *
     * @return lista de usuarios registrados
     */
    List<Usuario> findAll();

    /**
     * Guarda o actualiza un usuario en el sistema.
     *
     * @param usuario entidad a persistir
     * @return usuario guardado
     */
    Usuario save(Usuario usuario);

    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param email correo electrónico del usuario
     * @return usuario si existe, vacío si no
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Verifica si existe un usuario por su ID.
     *
     * @param id identificador del usuario
     * @return true si existe, false si no
     */
    boolean existsById(String id);
}