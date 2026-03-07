package co.edu.uniquindio.proyecto.domain;

import co.edu.uniquindio.proyecto.domain.entity.Administrador;
import co.edu.uniquindio.proyecto.domain.entity.Estudiante;
import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase {@link Solicitud}.
 *
 * <p>Esta clase verifica el comportamiento del agregado Solicitud
 * y valida que se cumplan las reglas de negocio relacionadas con
 * su ciclo de vida, cambios de estado y registro de eventos
 * en el historial.</p>
 */
class SolicitudTest {

    private Solicitud solicitud;
    private CodigoSolicitud id;
    private TipoSolicitud tipo;
    private Usuario admin;
    private Usuario estudiante;

    /**
     * Inicializa los objetos necesarios antes de ejecutar cada prueba.
     *
     * <p>Se crea una solicitud en estado inicial REGISTRADA junto
     * con usuarios de prueba para validar las diferentes reglas
     * del dominio.</p>
     */
    @BeforeEach
    void setup() {
        id = new CodigoSolicitud("EST-9876");
        tipo = TipoSolicitud.CANCELACION;
        estudiante = new Estudiante("1010", "Pepe", new Email("p@u.co"));
        admin = new Administrador("1", "Admin", new Email("admin@u.co"));
        solicitud = new Solicitud(id, tipo, "Descripción de prueba", CanalOrigen.CORREO, estudiante);
    }

    /**
     * Verifica que al crear una solicitud se registre automáticamente
     * un evento en el historial y que el estado inicial sea REGISTRADA.
     */
    @Test
    void debeRegistrarEventoAlCrearSolicitud() {
        assertFalse(solicitud.getHistorial().isEmpty());
        assertEquals(EstadoSolicitud.REGISTRADA, solicitud.getHistorial().get(0).estadoRelacionado());
    }

    /**
     * Verifica que no se permita realizar una transición inválida
     * dentro del ciclo de vida de la solicitud.
     *
     * <p>En este caso se intenta cerrar la solicitud directamente
     * sin haber sido atendida previamente.</p>
     */
    @Test
    void noDebePermitirTransicionInvalida() {
        assertThrows(IllegalStateException.class, () -> {
            solicitud.cerrar("Cierre prematuro", admin);
        });
    }

    /**
     * Verifica que una solicitud cerrada no pueda modificarse
     * posteriormente.
     *
     * <p>Después de completar el ciclo de vida hasta el estado
     * CERRADA, cualquier intento de modificación debe lanzar
     * una excepción.</p>
     */
    @Test
    void noDebeModificarSolicitudCerrada() {
        solicitud.clasificar(new Prioridad("MEDIA", "Justificación válida de prueba"));

        Usuario responsable = new Administrador("123", "Funcionario Académico", new Email("f@u.co"));

        solicitud.asignarResponsable(responsable);
        solicitud.marcarComoAtendida();
        solicitud.cerrar("Observación final", admin);

        assertThrows(IllegalStateException.class, () -> {
            solicitud.asignarResponsable(responsable);
        });
    }

    /**
     * Verifica que únicamente un usuario con rol de administrador
     * pueda cerrar una solicitud.
     */
    @Test
    void soloAdministradorPuedeCerrarSolicitud() {
        solicitud.clasificar(new Prioridad("ALTA", "Prioridad para cierre"));
        solicitud.asignarResponsable(admin);
        solicitud.marcarComoAtendida();

        assertThrows(IllegalStateException.class, () -> {
            solicitud.cerrar("Intento de cierre por estudiante", estudiante);
        });
    }

    /**
     * Verifica que el historial de eventos no pueda modificarse
     * desde fuera de la clase Solicitud.
     *
     * <p>El historial debe ser inmutable para garantizar la
     * integridad de los eventos registrados.</p>
     */
    @Test
    void historialDebeSerInmutableDesdeAfuera() {
        assertThrows(UnsupportedOperationException.class, () -> {
            solicitud.getHistorial().add(new EventoHistorial(null, "Hack", null));
        });
    }
}