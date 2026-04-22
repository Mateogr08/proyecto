package co.edu.uniquindio.proyecto.domain;

import co.edu.uniquindio.proyecto.domain.entity.*;
import co.edu.uniquindio.proyecto.domain.service.AsignacionResponsableService;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para el servicio {@link AsignacionResponsableService}.
 *
 * <p>Estas pruebas validan las reglas de negocio relacionadas con la
 * asignación y reasignación de responsables en una solicitud.</p>
 *
 * <p>Se enfoca en validar:</p>
 * <ul>
 *     <li>Autorización del actor (rol administrador)</li>
 *     <li>Condiciones del responsable (tipo y estado activo)</li>
 *     <li>Integración correcta con el agregado {@link Solicitud}</li>
 * </ul>
 */
class AsignacionResponsableServiceTest {

    private Solicitud solicitud;
    private Estudiante estudiante;
    private Administrador admin;
    private Profesor profesor;

    private AsignacionResponsableService service;

    /**
     * Configuración inicial para cada prueba.
     */
    @BeforeEach
    void setUp() {
        estudiante = new Estudiante("1", "Juan", new Email("e@u.com"),"123");
        admin = new Administrador("2", "Admin", new Email("a@u.com"),"345");
        profesor = new Profesor("3", "Profe", new Email("p@u.com"),"678");

        solicitud = new Solicitud(
                new CodigoSolicitud("SOL-001"),
                TipoSolicitud.CONSULTA,
                "Descripción",
                CanalOrigen.WEB,
                estudiante
        );

        // La solicitud debe estar clasificada antes de asignar responsable
        solicitud.clasificar(new Prioridad("ALTA", "Impacto alto detallado para el servicio"), admin);

        service = new AsignacionResponsableService();
    }

    /**
     * Verifica que un administrador puede asignar un responsable válido.
     */
    @Test
    void adminDebeAsignarResponsable() {
        service.asignarResponsable(solicitud, profesor, admin);

        assertEquals(EstadoSolicitud.EN_ATENCION, solicitud.getEstado());
    }

    /**
     * Verifica que un usuario que no es administrador no puede asignar responsables.
     */
    @Test
    void noAdminNoDebeAsignarResponsable() {
        assertThrows(IllegalStateException.class, () ->
                service.asignarResponsable(solicitud, profesor, estudiante)
        );
    }

    /**
     * Verifica que no se puede asignar un responsable inactivo.
     */
    @Test
    void noDebeAsignarResponsableInactivo() {
        profesor.desactivar();

        assertThrows(IllegalStateException.class, () ->
                service.asignarResponsable(solicitud, profesor, admin)
        );
    }
}
