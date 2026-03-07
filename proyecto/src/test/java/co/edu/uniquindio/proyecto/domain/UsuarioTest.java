package co.edu.uniquindio.proyecto.domain;

import co.edu.uniquindio.proyecto.domain.entity.Administrador;
import co.edu.uniquindio.proyecto.domain.entity.Estudiante;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UsuarioTest {
    @Test
    void losRolesDebenRetornarNombreCorrecto() {
        Usuario est = new Estudiante("1", "E", new Email("e@u.co"));
        Usuario adm = new Administrador("2", "A", new Email("a@u.co"));

        assertEquals("ESTUDIANTE", est.getRol());
        assertEquals("ADMINISTRADOR", adm.getRol());
    }
}