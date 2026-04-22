package co.edu.uniquindio.proyecto.domain;

import co.edu.uniquindio.proyecto.domain.entity.*;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolicitudTest {

    private Solicitud solicitud;
    private Estudiante estudiante;
    private Profesor profesor;
    private Administrador admin;

    @BeforeEach
    void setUp() {
        estudiante = new Estudiante("123", "Juan Pérez", new Email("juan@uniquindio.edu.co"),"1234");
        profesor = new Profesor("prof1", "Carlos Profe", new Email("profesor@uniquindio.edu.co"),"123456");
        admin = new Administrador("admin1", "Admin", new Email("admin@uniquindio.edu.co"),"12345679");

        solicitud = new Solicitud(
                new CodigoSolicitud("SOL-1"),
                TipoSolicitud.REGISTRO_ASIGNATURAS,
                "Problema con matrícula académica",
                CanalOrigen.WEB,
                estudiante
        );
    }

    @Test
    void deberiaCrearSolicitudCorrectamente() {
        assertEquals(EstadoSolicitud.REGISTRADA, solicitud.getEstado());
    }

    @Test
    void noDebeCrearSolicitudSinDescripcion() {
        assertThrows(IllegalArgumentException.class, () ->
                new Solicitud(new CodigoSolicitud("SOL-001"), TipoSolicitud.CONSULTA, "", CanalOrigen.WEB, estudiante)
        );
    }

    @Test
    void noDebeCrearSolicitudSiNoEsEstudiante() {
        assertThrows(IllegalArgumentException.class, () ->
                new Solicitud(new CodigoSolicitud("SOL-001"), TipoSolicitud.CUPO, "Descripción válida", CanalOrigen.WEB, profesor)
        );
    }

    @Test
    void deberiaClasificarSolicitud() {
        solicitud.clasificar(new Prioridad("ALTA", "Justificación detallada del impacto crítico"), admin);
        assertEquals(EstadoSolicitud.CLASIFICADA, solicitud.getEstado());
    }

    @Test
    void noDebeClasificarSiNoEstaEnRegistrada() {
        solicitud.clasificar(new Prioridad("ALTA", "Justificación detallada del impacto crítico"), admin);
        assertThrows(IllegalStateException.class, () ->
                solicitud.clasificar(new Prioridad("MEDIA", "Otra justificación válida"), admin)
        );
    }

    @Test
    void deberiaAsignarResponsable() {
        solicitud.clasificar(new Prioridad("MEDIA", "Justificación detallada de prioridad media"), admin);
        solicitud.asignarResponsable(profesor, admin);
        assertEquals(EstadoSolicitud.EN_ATENCION, solicitud.getEstado());
    }

    @Test
    void noDebeAsignarResponsableSinClasificar() {
        assertThrows(IllegalStateException.class, () ->
                solicitud.asignarResponsable(profesor, admin)
        );
    }

    @Test
    void deberiaMarcarComoAtendida() {
        solicitud.clasificar(new Prioridad("ALTA", "Urgencia máxima por el problema reportado"), admin);
        solicitud.asignarResponsable(profesor, admin);
        solicitud.marcarComoAtendida(profesor);
        assertEquals(EstadoSolicitud.ATENDIDA, solicitud.getEstado());
    }

    @Test
    void deberiaCerrarSolicitud() {
        solicitud.clasificar(new Prioridad("ALTA", "Urgencia máxima por el problema reportado"), admin);
        solicitud.asignarResponsable(profesor, admin);
        solicitud.marcarComoAtendida(profesor);
        solicitud.cerrar("Caso resuelto satisfactoriamente", admin);
        assertEquals(EstadoSolicitud.CERRADA, solicitud.getEstado());
    }

    @Test
    void noDebeCerrarSinEstarAtendida() {
        solicitud.clasificar(new Prioridad("MEDIA", "Justificación detallada de prioridad media"), admin);
        assertThrows(IllegalStateException.class, () ->
                solicitud.cerrar("Observación de cierre", admin)
        );
    }

    @Test
    void deberiaCancelarSolicitud() {
        solicitud.cancelar("Ya no es necesaria la solicitud", estudiante);
        assertEquals(EstadoSolicitud.CANCELADA, solicitud.getEstado());
    }

    @Test
    void noDebeCancelarSiEstaCerrada() {
        solicitud.clasificar(new Prioridad("ALTA", "Urgencia máxima por el problema reportado"), admin);
        solicitud.asignarResponsable(profesor, admin);
        solicitud.marcarComoAtendida(profesor);
        solicitud.cerrar("Finalizado", admin);

        assertThrows(IllegalStateException.class, () ->
                solicitud.cancelar("Motivo de cancelación", estudiante)
        );
    }

    @Test
    void deberiaRechazarSolicitud() {
        solicitud.clasificar(new Prioridad("BAJA", "Bajo impacto para el estudiante"), admin);
        solicitud.rechazar("No cumple con los requisitos mínimos", admin);
        assertEquals(EstadoSolicitud.RECHAZADA, solicitud.getEstado());
    }

    @Test
    void noDebeRechazarSinClasificar() {
        assertThrows(IllegalStateException.class, () ->
                solicitud.rechazar("Motivo de rechazo", admin)
        );
    }

    @Test
    void deberiaReasignarResponsable() {
        solicitud.clasificar(new Prioridad("ALTA", "Urgencia máxima por el problema reportado"), admin);
        solicitud.asignarResponsable(profesor, admin);
        Profesor otro = new Profesor("2", "Otro", new Email("otro@uni.com"),"0987");
        solicitud.reasignarResponsable(otro, admin);
        assertEquals(EstadoSolicitud.EN_ATENCION, solicitud.getEstado());
    }

    @Test
    void deberiaCambiarPrioridad() {
        solicitud.cambiarPrioridad(new Prioridad("BAJA", "Menor impacto detallado para el cambio"), admin);
        assertEquals("BAJA", solicitud.getPrioridad().nivel());
    }

    @Test
    void deberiaReabrirSolicitud() {
        solicitud.clasificar(new Prioridad("ALTA", "Urgencia máxima por el problema reportado"), admin);
        solicitud.asignarResponsable(profesor, admin);
        solicitud.marcarComoAtendida(profesor);
        solicitud.cerrar("ok", admin);
        solicitud.reabrir("Nuevo inconveniente reportado por el usuario", admin);
        assertEquals(EstadoSolicitud.EN_ATENCION, solicitud.getEstado());
    }

    @Test
    void noDebeReabrirSiNoEstaCerrada() {
        assertThrows(IllegalStateException.class, () ->
                solicitud.reabrir("Motivo de reapertura", admin)
        );
    }
}