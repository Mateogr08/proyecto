package co.edu.uniquindio.proyecto.domain;

import co.edu.uniquindio.proyecto.domain.entity.*;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la entidad {@link Solicitud}.
 *
 * <p>Estas pruebas validan exclusivamente las reglas internas del agregado,
 * tales como:
 * <ul>
 *     <li>Creación de la solicitud</li>
 *     <li>Transiciones de estado</li>
 *     <li>Restricciones propias del ciclo de vida</li>
 * </ul>
 *
 * <p>No se validan reglas de autorización ni roles de usuario, ya que dichas
 * responsabilidades pertenecen a los servicios de dominio.</p>
 */
class SolicitudTest {

    private Solicitud solicitud;
    private Estudiante estudiante;
    private Profesor profesor;

    /**
     * Configuración inicial para cada prueba.
     *
     * <p>Se crea un estudiante válido y una solicitud en estado inicial
     * REGISTRADA.</p>
     */
    @BeforeEach
    void setUp() {
        estudiante = new Estudiante(
                "123",
                "Juan Pérez",
                new Email("juan@uniquindio.edu.co")
        );

        profesor = new Profesor(
                "prof1",
                "Carlos Profe",
                new Email("profesor@uniquindio.edu.co")
        );

        solicitud = new Solicitud(
                new CodigoSolicitud("SOL-1"),
                TipoSolicitud.REGISTRO_ASIGNATURAS,
                "Problema con matrícula",
                CanalOrigen.WEB,
                estudiante
        );
    }

    // =============================================================================================
    // ------------------------------------ CREACIÓN -----------------------------------------------
    // =============================================================================================

    /**
     * Verifica que la solicitud se crea correctamente en estado REGISTRADA.
     */
    @Test
    void deberiaCrearSolicitudCorrectamente() {
        assertEquals(EstadoSolicitud.REGISTRADA, solicitud.getEstado());
    }

    /**
     * No permite crear una solicitud sin descripción.
     */
    @Test
    void noDebeCrearSolicitudSinDescripcion() {
        assertThrows(IllegalArgumentException.class, () ->
                new Solicitud(
                        new CodigoSolicitud("1"),
                        TipoSolicitud.CONSULTA,
                        "",
                        CanalOrigen.WEB,
                        estudiante
                )
        );
    }

    /**
     * Solo un estudiante puede crear solicitudes.
     */
    @Test
    void noDebeCrearSolicitudSiNoEsEstudiante() {
        Profesor profesor = new Profesor(
                "1",
                "Profe",
                new Email("profe@uni.com")
        );

        assertThrows(IllegalArgumentException.class, () ->
                new Solicitud(
                        new CodigoSolicitud("1"),
                        TipoSolicitud.CUPO,
                        "Descripción válida",
                        CanalOrigen.WEB,
                        profesor
                )
        );
    }

    // =============================================================================================
    // ------------------------------------ CLASIFICACIÓN ------------------------------------------
    // =============================================================================================

    /**
     * Permite clasificar una solicitud en estado REGISTRADA.
     */
    @Test
    void deberiaClasificarSolicitud() {
        solicitud.clasificar(new Prioridad("ALTA", "Impacto crítico"));

        assertEquals(EstadoSolicitud.CLASIFICADA, solicitud.getEstado());
    }

    /**
     * No permite clasificar si la solicitud no está en estado REGISTRADA.
     */
    @Test
    void noDebeClasificarSiNoEstaEnRegistrada() {
        solicitud.clasificar(new Prioridad("ALTA", "Impacto crítico"));

        assertThrows(IllegalStateException.class, () ->
                solicitud.clasificar(new Prioridad("MEDIA", "Otro"))
        );
    }

    // =============================================================================================
    // ------------------------------------ ASIGNACIÓN ---------------------------------------------
    // =============================================================================================

    /**
     * Permite asignar un responsable cuando la solicitud está clasificada.
     */
    @Test
    void deberiaAsignarResponsable() {
        solicitud.clasificar(new Prioridad("MEDIA", "Normal"));
        solicitud.asignarResponsable(profesor);

        assertEquals(EstadoSolicitud.EN_ATENCION, solicitud.getEstado());
    }

    /**
     * No permite asignar responsable si la solicitud no está clasificada.
     */
    @Test
    void noDebeAsignarResponsableSinClasificar() {
        assertThrows(IllegalStateException.class, () ->
                solicitud.asignarResponsable(profesor)
        );
    }

    // =============================================================================================
    // ------------------------------------ ATENCIÓN -----------------------------------------------
    // =============================================================================================

    /**
     * Permite marcar la solicitud como atendida si está en atención.
     */
    @Test
    void deberiaMarcarComoAtendida() {
        solicitud.clasificar(new Prioridad("ALTA", "Urgente"));
        solicitud.asignarResponsable(profesor);

        solicitud.marcarComoAtendida();

        assertEquals(EstadoSolicitud.ATENDIDA, solicitud.getEstado());
    }

    // =============================================================================================
    // ------------------------------------ CIERRE -------------------------------------------------
    // =============================================================================================

    /**
     * Permite cerrar una solicitud que ha sido atendida.
     */
    @Test
    void deberiaCerrarSolicitud() {
        solicitud.clasificar(new Prioridad("ALTA", "Urgente"));
        solicitud.asignarResponsable(profesor);
        solicitud.marcarComoAtendida();

        solicitud.cerrar("Caso resuelto");

        assertEquals(EstadoSolicitud.CERRADA, solicitud.getEstado());
    }

    /**
     * No permite cerrar si la solicitud no ha sido atendida.
     */
    @Test
    void noDebeCerrarSinEstarAtendida() {
        solicitud.clasificar(new Prioridad("MEDIA", "Normal"));

        assertThrows(IllegalStateException.class, () ->
                solicitud.cerrar("Observación")
        );
    }

    // =============================================================================================
    // ------------------------------------ CANCELACIÓN --------------------------------------------
    // =============================================================================================

    /**
     * Permite cancelar una solicitud en estados válidos.
     */
    @Test
    void deberiaCancelarSolicitud() {
        solicitud.cancelar("Ya no es necesaria");

        assertEquals(EstadoSolicitud.CANCELADA, solicitud.getEstado());
    }

    /**
     * No permite cancelar una solicitud cerrada.
     */
    @Test
    void noDebeCancelarSiEstaCerrada() {
        solicitud.clasificar(new Prioridad("ALTA", "Urgente"));
        solicitud.asignarResponsable(profesor);
        solicitud.marcarComoAtendida();
        solicitud.cerrar("ok");

        assertThrows(IllegalStateException.class, () ->
                solicitud.cancelar("Motivo")
        );
    }

    // =============================================================================================
    // ------------------------------------ RECHAZO ------------------------------------------------
    // =============================================================================================

    /**
     * Permite rechazar una solicitud clasificada.
     */
    @Test
    void deberiaRechazarSolicitud() {
        solicitud.clasificar(new Prioridad("BAJA", "No aplica"));

        solicitud.rechazar("No cumple requisitos");

        assertEquals(EstadoSolicitud.RECHAZADA, solicitud.getEstado());
    }

    /**
     * No permite rechazar si no está clasificada.
     */
    @Test
    void noDebeRechazarSinClasificar() {
        assertThrows(IllegalStateException.class, () ->
                solicitud.rechazar("Motivo")
        );
    }

    // =============================================================================================
    // ------------------------------------ REASIGNACIÓN -------------------------------------------
    // =============================================================================================

    /**
     * Permite reasignar el responsable en estado EN_ATENCION.
     */
    @Test
    void deberiaReasignarResponsable() {
        solicitud.clasificar(new Prioridad("ALTA", "Urgente"));
        solicitud.asignarResponsable(profesor);

        Profesor otro = new Profesor("2", "Otro", new Email("otro@uni.com"));

        solicitud.reasignarResponsable(otro);

        assertEquals(EstadoSolicitud.EN_ATENCION, solicitud.getEstado());
    }

    // =============================================================================================
    // ------------------------------------ PRIORIDAD ----------------------------------------------
    // =============================================================================================

    /**
     * Permite cambiar la prioridad mientras la solicitud no esté cerrada.
     */
    @Test
    void deberiaCambiarPrioridad() {
        solicitud.cambiarPrioridad(new Prioridad("BAJA", "Menor impacto"));
    }

    // =============================================================================================
    // ------------------------------------ REAPERTURA ---------------------------------------------
    // =============================================================================================

    /**
     * Permite reabrir una solicitud cerrada.
     */
    @Test
    void deberiaReabrirSolicitud() {
        solicitud.clasificar(new Prioridad("ALTA", "Urgente"));
        solicitud.asignarResponsable(profesor);
        solicitud.marcarComoAtendida();
        solicitud.cerrar("ok");

        solicitud.reabrir("Nuevo inconveniente");

        assertEquals(EstadoSolicitud.EN_ATENCION, solicitud.getEstado());
    }

    /**
     * No permite reabrir si la solicitud no está cerrada.
     */
    @Test
    void noDebeReabrirSiNoEstaCerrada() {
        assertThrows(IllegalStateException.class, () ->
                solicitud.reabrir("Motivo")
        );
    }
}