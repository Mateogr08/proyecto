package co.edu.uniquindio.proyecto.domain;

import co.edu.uniquindio.proyecto.domain.entity.*;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase {@link Solicitud}.
 *
 * <p>Valida el ciclo de vida de una solicitud, sus transiciones de estado,
 * y las restricciones según el rol del usuario.</p>
 */
class SolicitudTest {

    private Solicitud solicitud;
    private Estudiante estudiante;
    private Administrador admin;
    private Profesor profesor;

    /**
     * Configuración inicial antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
        estudiante = new Estudiante(
                "123",
                "Juan Pérez",
                new Email("juan@uniquindio.edu.co")
        );

        admin = new Administrador(
                "admin1",
                "Ana Admin",
                new Email("admin@uniquindio.edu.co")
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
    // ------------------------------------CREACIÓN DE LA SOLICITUD---------------------------------
    // =============================================================================================

    /**
     * Verifica que la solicitud se crea correctamente.
     */
    @Test
    void deberiaCrearSolicitudCorrectamente() {
        assertEquals(EstadoSolicitud.REGISTRADA, solicitud.getEstado());
        assertFalse(solicitud.getHistorial().isEmpty());
    }

    /**
     * No permite crear sin descripción.
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
     * Solo estudiantes pueden crear solicitudes.
     */
    @Test
    void noDebeCrearSolicitudSiNoEsEstudiante() {
        assertThrows(IllegalArgumentException.class, () ->
                new Solicitud(
                        new CodigoSolicitud("1"),
                        TipoSolicitud.CUPO,
                        "Descripción válida",
                        CanalOrigen.WEB,
                        admin
                )
        );
    }

    // ==============================================================================================
    // ----------------------------------------CLASIFICAR SOLICITUD---------------------------------
    // ==============================================================================================

    /**
     * Un administrador puede clasificar la solicitud.
     */
    @Test
    void adminDeberiaClasificarSolicitud() {
        Prioridad prioridad = new Prioridad(
                "ALTA",
                "Impacta directamente el proceso académico del estudiante"
        );

        solicitud.clasificar(prioridad, admin);

        assertEquals(EstadoSolicitud.CLASIFICADA, solicitud.getEstado());
    }

    /**
     * Un usuario no administrador no puede clasificar.
     */
    @Test
    void noAdminNoPuedeClasificar() {
        Prioridad prioridad = new Prioridad(
                "ALTA",
                "Impacta directamente el proceso académico del estudiante"
        );

        assertThrows(IllegalStateException.class, () ->
                solicitud.clasificar(prioridad, estudiante)
        );
    }

    // ===============================================================================================
    // _____________________________________ASIGNAR RESPONSABLE A LA SOLICITUD_________________________
    // ===============================================================================================

    /**
     * Verifica asignación correcta de responsable.
     */
    @Test
    void deberiaAsignarResponsableCorrectamente() {
        Prioridad prioridad = new Prioridad(
                "MEDIA",
                "Requiere atención en el corto plazo sin urgencia crítica"
        );

        solicitud.clasificar(prioridad, admin);
        solicitud.asignarResponsable(profesor, admin);

        assertEquals(EstadoSolicitud.EN_ATENCION, solicitud.getEstado());
    }

    /**
     * No permite asignar responsable sin clasificar.
     */
    @Test
    void noDebeAsignarSiNoEstaClasificada() {
        assertThrows(IllegalStateException.class, () ->
                solicitud.asignarResponsable(profesor, admin)
        );
    }

    // ===============================================================================================
    // -------------------------------------------ATENDER LA SOLICITUD--------------------------------
    // ===============================================================================================

    /**
     * El responsable puede marcar la solicitud como atendida.
     */
    @Test
    void responsableDebeMarcarComoAtendida() {
        Prioridad prioridad = new Prioridad(
                "ALTA",
                "Bloquea un proceso académico importante"
        );

        solicitud.clasificar(prioridad, admin);
        solicitud.asignarResponsable(profesor, admin);

        solicitud.marcarComoAtendida(profesor);

        assertEquals(EstadoSolicitud.ATENDIDA, solicitud.getEstado());
    }

    /**
     * Otro usuario no puede atender la solicitud.
     */
    @Test
    void otroUsuarioNoDebeAtender() {
        Prioridad prioridad = new Prioridad(
                "ALTA",
                "Bloquea un proceso académico importante"
        );

        solicitud.clasificar(prioridad, admin);
        solicitud.asignarResponsable(profesor, admin);

        assertThrows(IllegalStateException.class, () ->
                solicitud.marcarComoAtendida(admin)
        );
    }

    // ============================================================================================
    // ---------------------------------------CERRAR LA SOLICITUD---------------------------------
    // ============================================================================================

    /**
     * Un administrador puede cerrar la solicitud.
     */
    @Test
    void adminDebeCerrarSolicitud() {
        Prioridad prioridad = new Prioridad(
                "ALTA",
                "Requiere solución inmediata por impacto académico"
        );

        solicitud.clasificar(prioridad, admin);
        solicitud.asignarResponsable(profesor, admin);
        solicitud.marcarComoAtendida(profesor);

        solicitud.cerrar("Caso resuelto correctamente", admin);

        assertEquals(EstadoSolicitud.CERRADA, solicitud.getEstado());
    }

    /**
     * No se puede cerrar sin haber sido atendida.
     */
    @Test
    void noDebeCerrarSinAtender() {
        Prioridad prioridad = new Prioridad(
                "MEDIA",
                "No es urgente pero requiere atención"
        );

        solicitud.clasificar(prioridad, admin);

        assertThrows(IllegalStateException.class, () ->
                solicitud.cerrar("Observación válida de cierre", admin)
        );
    }

    // ================================================================================================
    // ------------------------------------------CANCELAR LA SOLICITUD--------------------------------
    // ================================================================================================

    /**
     * El estudiante solicitante puede cancelar.
     */
    @Test
    void estudianteDebeCancelar() {
        solicitud.cancelar("Ya no es necesaria la solicitud", estudiante);

        assertEquals(EstadoSolicitud.CANCELADA, solicitud.getEstado());
    }

    /**
     * El administrador puede cancelar.
     */
    @Test
    void adminPuedeCancelar() {
        solicitud.cancelar("Cancelación administrativa válida", admin);

        assertEquals(EstadoSolicitud.CANCELADA, solicitud.getEstado());
    }

    /**
     * Usuario no autorizado no puede cancelar.
     */
    @Test
    void noDebeCancelarUsuarioNoValido() {
        assertThrows(IllegalStateException.class, () ->
                solicitud.cancelar("Motivo válido de cancelación", profesor)
        );
    }

    // ==========================================================================================
    // -------------------------------------RECHAZAR LA SOLICITUD---------------------------------
    // ===========================================================================================

    /**
     * Un administrador puede rechazar la solicitud.
     */
    @Test
    void adminDebeRechazar() {
        Prioridad prioridad = new Prioridad(
                "BAJA",
                "No cumple con los criterios establecidos"
        );

        solicitud.clasificar(prioridad, admin);

        solicitud.rechazar("No aplica para este proceso", admin);

        assertEquals(EstadoSolicitud.RECHAZADA, solicitud.getEstado());
    }

    /**
     * No se puede rechazar si no está clasificada.
     */
    @Test
    void noDebeRechazarSiNoEstaClasificada() {
        assertThrows(IllegalStateException.class, () ->
                solicitud.rechazar("Motivo válido", admin)
        );
    }

    // ========================================================================================
    // ---------------------------------REASIGNAR LA SOLICITUD---------------------------------
    // ========================================================================================

    /**
     * Permite reasignar el responsable.
     */
    @Test
    void adminDebeReasignarResponsable() {
        Prioridad prioridad = new Prioridad(
                "ALTA",
                "Impacto directo en el proceso académico"
        );

        solicitud.clasificar(prioridad, admin);
        solicitud.asignarResponsable(profesor, admin);

        Administrador otroAdmin = new Administrador(
                "admin2",
                "Otro Admin",
                new Email("admin2@uniquindio.edu.co")
        );

        solicitud.reasignarResponsable(otroAdmin, admin);

        assertEquals(EstadoSolicitud.EN_ATENCION, solicitud.getEstado());
    }

    // =======================================================================================
    // -----------------------------CAMBIAR PRIORIDAD DE LA SOLICITUD-------------------------
    // =======================================================================================

    /**
     * Permite cambiar la prioridad de la solicitud.
     */
    @Test
    void adminDebeCambiarPrioridad() {
        Prioridad nueva = new Prioridad(
                "BAJA",
                "No afecta procesos críticos del sistema"
        );

        solicitud.cambiarPrioridad(nueva, admin);

        assertNotNull(solicitud.getHistorial());
    }

    // ==========================================================================================
    // ------------------------------------REABRIR LA SOLICITUD----------------------------------
    // ==========================================================================================

    /**
     * Permite reabrir una solicitud cerrada.
     */
    @Test
    void adminDebeReabrirSolicitud() {
        Prioridad prioridad = new Prioridad(
                "ALTA",
                "Caso crítico que requiere seguimiento"
        );

        solicitud.clasificar(prioridad, admin);
        solicitud.asignarResponsable(profesor, admin);
        solicitud.marcarComoAtendida(profesor);
        solicitud.cerrar("Cierre correcto del caso", admin);

        solicitud.reabrir("Se detectó un nuevo inconveniente", admin);

        assertEquals(EstadoSolicitud.EN_ATENCION, solicitud.getEstado());
    }

    /**
     * No se puede reabrir si no está cerrada.
     */
    @Test
    void noDebeReabrirSiNoEstaCerrada() {
        assertThrows(IllegalStateException.class, () ->
                solicitud.reabrir("Motivo válido para reapertura", admin)
        );
    }
}