package co.edu.uniquindio.proyecto.domain;

import co.edu.uniquindio.proyecto.domain.entity.Administrador;
import co.edu.uniquindio.proyecto.domain.entity.Estudiante;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Pruebas unitarias para la jerarquía de la entidad {@link Usuario}.
 *
 * <p>Esta clase valida que las implementaciones concretas de Usuario
 * (como Estudiante y Administrador) retornen correctamente el nombre
 * del rol correspondiente dentro del sistema.</p>
 */
class UsuarioTest {

    /**
     * Verifica que cada tipo de usuario retorne correctamente
     * el nombre de su rol.
     *
     * <p>Se crean instancias de Estudiante y Administrador y se
     * comprueba que el metodo {@code getRol()} devuelva el valor
     * esperado.</p>
     */
    @Test
    void losRolesDebenRetornarNombreCorrecto() {
        Usuario est = new Estudiante("1", "E", new Email("e@u.co"));
        Usuario adm = new Administrador("2", "A", new Email("a@u.co"));

        assertEquals("ESTUDIANTE", est.getRol());
        assertEquals("ADMINISTRADOR", adm.getRol());
    }
}