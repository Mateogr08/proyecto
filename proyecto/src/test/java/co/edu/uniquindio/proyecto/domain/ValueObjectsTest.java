package co.edu.uniquindio.proyecto.domain;

import co.edu.uniquindio.proyecto.domain.valueobject.CodigoSolicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValueObjectsTest {

    @Test
    void emailDebeValidarFormatoCorrecto() {
        Email email = new Email("test@uniquindio.edu.co");
        assertEquals("test@uniquindio.edu.co", email.getDireccion());
        assertThrows(IllegalArgumentException.class, () -> new Email("correo-sin-arroba"));
    }

    @Test
    void codigoSolicitudDebeCumplirPatron() {
        assertDoesNotThrow(() -> new CodigoSolicitud("ACA-1234"));
        assertThrows(IllegalArgumentException.class, () -> new CodigoSolicitud("123-ABCD"));
    }

    @Test
    void prioridadDebeTenerJustificacionLarga() {
        assertThrows(IllegalArgumentException.class, () -> new Prioridad("ALTA", "Corta"));
    }
}