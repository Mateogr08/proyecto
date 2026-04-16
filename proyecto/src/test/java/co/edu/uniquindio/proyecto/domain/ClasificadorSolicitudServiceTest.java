package co.edu.uniquindio.proyecto.domain;

import co.edu.uniquindio.proyecto.domain.entity.*;
import co.edu.uniquindio.proyecto.domain.service.ClasificadorSolicitudService;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para el servicio {@link ClasificadorSolicitudService}.
 *
 * <p>Este conjunto de pruebas valida las reglas de negocio asociadas a la
 * clasificación de solicitudes, específicamente la asignación de prioridad.</p>
 *
 * <p>Se verifica:</p>
 * <ul>
 *     <li>Que solo un administrador pueda clasificar solicitudes</li>
 *     <li>Que la clasificación actualice correctamente el estado de la solicitud</li>
 * </ul>
 */
class ClasificadorSolicitudServiceTest {

    private Solicitud solicitud;
    private Estudiante estudiante;
    private Administrador admin;

    private ClasificadorSolicitudService service;

    /**
     * Inicializa los objetos necesarios antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
        estudiante = new Estudiante("1", "Juan", new Email("e@u.com"));
        admin = new Administrador("2", "Admin", new Email("a@u.com"));

        solicitud = new Solicitud(
                new CodigoSolicitud("1"),
                TipoSolicitud.CONSULTA,
                "Descripción",
                CanalOrigen.WEB,
                estudiante
        );

        service = new ClasificadorSolicitudService();
    }

    /**
     * Verifica que un administrador puede clasificar una solicitud correctamente.
     */
    @Test
    void adminDebeClasificarSolicitud() {
        service.clasificarSolicitud(
                solicitud,
                new Prioridad("ALTA", "Impacto crítico"),
                admin
        );

        assertEquals(EstadoSolicitud.CLASIFICADA, solicitud.getEstado());
    }

    /**
     * Verifica que un usuario no administrador no puede clasificar solicitudes.
     */
    @Test
    void noAdminNoDebeClasificarSolicitud() {
        assertThrows(IllegalStateException.class, () ->
                service.clasificarSolicitud(
                        solicitud,
                        new Prioridad("ALTA", "Impacto crítico"),
                        estudiante
                )
        );
    }
}
