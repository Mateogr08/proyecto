package co.edu.uniquindio.proyecto.domain;

import co.edu.uniquindio.proyecto.domain.valueobject.CodigoSolicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para los Value Objects del dominio.
 *
 * <p>Estas pruebas validan las reglas de negocio, restricciones
 * e invariantes definidas en los objetos de valor:</p>
 *
 * <ul>
 *     <li>{@link Email}</li>
 *     <li>{@link CodigoSolicitud}</li>
 *     <li>{@link Prioridad}</li>
 * </ul>
 *
 * <p>Se verifica que:</p>
 * <ul>
 *     <li>Los valores válidos sean aceptados correctamente</li>
 *     <li>Los valores inválidos generen excepciones</li>
 *     <li>Se mantenga la integridad del dominio</li>
 * </ul>
 */
class ValueObjectsTest {

    // =============================================================================================
    // ---------------------------------------- EMAIL ----------------------------------------------
    // =============================================================================================

    /**
     * Verifica que se pueda crear un Email válido correctamente.
     */
    @Test
    void deberiaCrearEmailValido() {
        Email email = new Email("test@uniquindio.edu.co");

        assertEquals("test@uniquindio.edu.co", email.getDireccion());
    }

    /**
     * Verifica que un email con formato inválido lance excepción.
     */
    @Test
    void noDebeCrearEmailInvalido() {
        assertThrows(IllegalArgumentException.class, () ->
                new Email("correo-sin-arroba")
        );
    }

    /**
     * Verifica que no se permita un email nulo o vacío.
     */
    @Test
    void noDebeCrearEmailVacioONulo() {
        assertThrows(IllegalArgumentException.class, () ->
                new Email("")
        );

        assertThrows(IllegalArgumentException.class, () ->
                new Email(null)
        );
    }

    // =============================================================================================
    // ----------------------------------- CODIGO SOLICITUD ----------------------------------------
    // =============================================================================================

    /**
     * Verifica que se pueda crear un código de solicitud válido.
     */
    @Test
    void deberiaCrearCodigoSolicitudValido() {
        assertDoesNotThrow(() ->
                new CodigoSolicitud("SOL-001")
        );
    }

    /**
     * Verifica que un código con formato inválido lance excepción.
     */
    @Test
    void noDebeCrearCodigoSolicitudInvalido() {
        assertThrows(IllegalArgumentException.class, () ->
                new CodigoSolicitud("123-ABC")
        );
    }

    /**
     * Verifica que no se permita un código nulo o vacío.
     */
    @Test
    void noDebeCrearCodigoSolicitudVacioONulo() {
        assertThrows(IllegalArgumentException.class, () ->
                new CodigoSolicitud("")
        );

        assertThrows(IllegalArgumentException.class, () ->
                new CodigoSolicitud(null)
        );
    }

    // =============================================================================================
    // ---------------------------------------- PRIORIDAD ------------------------------------------
    // =============================================================================================

    /**
     * Verifica que se pueda crear una prioridad válida.
     */
    @Test
    void deberiaCrearPrioridadValida() {
        assertDoesNotThrow(() ->
                new Prioridad("ALTA", "Impacta el proceso académico del estudiante")
        );
    }

    /**
     * Verifica que una prioridad con justificación corta lance excepción.
     */
    @Test
    void noDebeCrearPrioridadConJustificacionCorta() {
        assertThrows(IllegalArgumentException.class, () ->
                new Prioridad("ALTA", "Corta")
        );
    }

    /**
     * Verifica que no se permita una prioridad con valores nulos.
     */
    @Test
    void noDebeCrearPrioridadConValoresNulos() {
        assertThrows(IllegalArgumentException.class, () ->
                new Prioridad(null, "Justificación válida")
        );

        assertThrows(IllegalArgumentException.class, () ->
                new Prioridad("ALTA", null)
        );
    }
}