package co.edu.uniquindio.proyecto.domain;

import co.edu.uniquindio.proyecto.domain.entity.*;
import co.edu.uniquindio.proyecto.domain.service.GestionSolicitudService;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para el servicio {@link GestionSolicitudService}.
 *
 * <p>Estas pruebas validan las reglas de autorización y comportamiento
 * en las operaciones del ciclo de vida de una solicitud.</p>
 *
 * <p>Incluye validaciones para:</p>
 * <ul>
 *     <li>Cierre de solicitudes</li>
 *     <li>Cancelación</li>
 *     <li>Restricciones de acceso según el usuario</li>
 * </ul>
 */
class GestionSolicitudServiceTest {

    private Solicitud solicitud;
    private Estudiante estudiante;
    private Administrador admin;
    private Profesor profesor;

    private GestionSolicitudService service;

    /**
     * Configuración inicial común para las pruebas.
     */
    @BeforeEach
    void setUp() {
        estudiante = new Estudiante("1", "Juan", new Email("e@u.com"));
        admin = new Administrador("2", "Admin", new Email("a@u.com"));
        profesor = new Profesor("3", "Profe", new Email("p@u.com"));

        solicitud = new Solicitud(
                new CodigoSolicitud("1"),
                TipoSolicitud.CONSULTA,
                "Descripción",
                CanalOrigen.WEB,
                estudiante
        );

        // Se lleva la solicitud a estado ATENDIDA
        solicitud.clasificar(new Prioridad("ALTA", "Impacto"));
        solicitud.asignarResponsable(profesor);
        solicitud.marcarComoAtendida();

        service = new GestionSolicitudService();
    }

    /**
     * Verifica que un administrador puede cerrar una solicitud correctamente.
     */
    @Test
    void adminDebeCerrarSolicitud() {
        service.cerrarSolicitud(solicitud, "Caso resuelto", admin);

        assertEquals(EstadoSolicitud.CERRADA, solicitud.getEstado());
    }

    /**
     * Verifica que el estudiante solicitante puede cancelar su solicitud.
     */
    @Test
    void estudianteDebeCancelarSolicitud() {
        service.cancelarSolicitud(solicitud, "Ya no es necesaria", estudiante);

        assertEquals(EstadoSolicitud.CANCELADA, solicitud.getEstado());
    }

    /**
     * Verifica que un usuario no autorizado no puede cancelar la solicitud.
     */
    @Test
    void usuarioNoAutorizadoNoDebeCancelar() {
        Profesor otro = new Profesor("4", "Otro", new Email("otro@u.com"));

        assertThrows(IllegalStateException.class, () ->
                service.cancelarSolicitud(solicitud, "Motivo", otro)
        );
    }
}
