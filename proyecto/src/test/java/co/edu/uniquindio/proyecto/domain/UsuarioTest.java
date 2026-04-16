package co.edu.uniquindio.proyecto.domain;

import co.edu.uniquindio.proyecto.domain.entity.Administrador;
import co.edu.uniquindio.proyecto.domain.entity.Estudiante;
import co.edu.uniquindio.proyecto.domain.entity.Profesor;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la jerarquía de la entidad {@link Usuario}.
 *
 * <p>Estas pruebas validan el comportamiento relacionado con el tipo de usuario,
 * utilizando los métodos de verificación como {@code esAdministrador()},
 * {@code esProfesor()} y {@code esEstudiante()}.</p>
 *
 * <p>No se validan roles como texto, ya que el diseño actual encapsula
 * esta lógica mediante comportamiento y no mediante strings.</p>
 */
class UsuarioTest {

    /**
     * Verifica que un usuario de tipo Estudiante sea identificado correctamente.
     */
    @Test
    void deberiaIdentificarEstudianteCorrectamente() {
        Usuario estudiante = new Estudiante(
                "1",
                "Juan",
                new Email("juan@uniquindio.edu.co")
        );

        assertTrue(estudiante.esEstudiante());
        assertFalse(estudiante.esAdministrador());
        assertFalse(estudiante.esProfesor());
    }

    /**
     * Verifica que un usuario de tipo Administrador sea identificado correctamente.
     */
    @Test
    void deberiaIdentificarAdministradorCorrectamente() {
        Usuario admin = new Administrador(
                "2",
                "Ana",
                new Email("admin@uniquindio.edu.co")
        );

        assertTrue(admin.esAdministrador());
        assertFalse(admin.esEstudiante());
        assertFalse(admin.esProfesor());
    }

    /**
     * Verifica que un usuario de tipo Profesor sea identificado correctamente.
     */
    @Test
    void deberiaIdentificarProfesorCorrectamente() {
        Usuario profesor = new Profesor(
                "3",
                "Carlos",
                new Email("profesor@uniquindio.edu.co")
        );

        assertTrue(profesor.esProfesor());
        assertFalse(profesor.esAdministrador());
        assertFalse(profesor.esEstudiante());
    }

    /**
     * Verifica el estado activo/inactivo del usuario.
     */
    @Test
    void deberiaCambiarEstadoActivo() {
        Usuario usuario = new Estudiante(
                "4",
                "Laura",
                new Email("laura@uniquindio.edu.co")
        );

        assertTrue(usuario.estaActivo());

        usuario.desactivar();
        assertFalse(usuario.estaActivo());

        usuario.activar();
        assertTrue(usuario.estaActivo());
    }

    /**
     * Verifica que dos usuarios con la misma identificación sean iguales.
     */
    @Test
    void usuariosConMismaIdentificacionDebenSerIguales() {
        Usuario u1 = new Estudiante(
                "123",
                "Juan",
                new Email("juan1@uni.com")
        );

        Usuario u2 = new Estudiante(
                "123",
                "Pedro",
                new Email("juan2@uni.com")
        );

        assertEquals(u1, u2);
    }
}