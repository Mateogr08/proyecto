package co.edu.uniquindio.proyecto.domain;

import co.edu.uniquindio.proyecto.domain.valueobject.CodigoSolicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para los Value Objects del dominio.
 *
 * <p>Esta clase valida las reglas de negocio y restricciones
 * definidas en los objetos de valor como Email, CodigoSolicitud
 * y Prioridad. Se comprueba que los datos válidos sean aceptados
 * y que los datos inválidos generen las excepciones correspondientes.</p>
 */
class ValueObjectsTest {

    /**
     * Verifica que el objeto de valor {@link Email} valide correctamente
     * el formato de una dirección de correo electrónico.
     *
     * <p>Se espera que un correo con formato válido sea aceptado y que
     * un correo con formato inválido lance una excepción.</p>
     */
    @Test
    void emailDebeValidarFormatoCorrecto() {
        Email email = new Email("test@uniquindio.edu.co");
        assertEquals("test@uniquindio.edu.co", email.getDireccion());

        assertThrows(IllegalArgumentException.class, () -> new Email("correo-sin-arroba"));
    }

    /**
     * Verifica que el objeto de valor {@link CodigoSolicitud}
     * cumpla con el patrón establecido para los códigos.
     *
     * <p>Un código válido debe seguir el formato definido
     * por el sistema, mientras que uno inválido debe generar
     * una excepción.</p>
     */
    @Test
    void codigoSolicitudDebeCumplirPatron() {
        assertDoesNotThrow(() -> new CodigoSolicitud("ACA-1234"));
        assertThrows(IllegalArgumentException.class, () -> new CodigoSolicitud("123-ABCD"));
    }

    /**
     * Verifica que el objeto de valor {@link Prioridad}
     * requiera una justificación con longitud suficiente.
     *
     * <p>Si la justificación es demasiado corta, el sistema
     * debe lanzar una excepción para evitar datos inválidos
     * en el dominio.</p>
     */
    @Test
    void prioridadDebeTenerJustificacionLarga() {
        assertThrows(IllegalArgumentException.class, () -> new Prioridad("ALTA", "Corta"));
    }
}