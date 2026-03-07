package co.edu.uniquindio.proyecto.domain;

import co.edu.uniquindio.proyecto.domain.entity.Administrador;
import co.edu.uniquindio.proyecto.domain.entity.Estudiante;
import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolicitudTest {

    private Solicitud solicitud;
    private CodigoSolicitud id;
    private TipoSolicitud tipo;
    private Usuario admin;
    private Usuario estudiante;

    @BeforeEach
    void setup() {
        id = new CodigoSolicitud("EST-9876");
        tipo = TipoSolicitud.CANCELACION;
        estudiante = new Estudiante("1010", "Pepe", new Email("p@u.co"));
        admin = new Administrador("1", "Admin", new Email("admin@u.co"));
        solicitud = new Solicitud(id, tipo, "Descripción de prueba", CanalOrigen.CORREO, estudiante);
    }

    @Test
    void debeRegistrarEventoAlCrearSolicitud() {
        assertFalse(solicitud.getHistorial().isEmpty());
        assertEquals(EstadoSolicitud.REGISTRADA, solicitud.getHistorial().get(0).estadoRelacionado());
    }

    @Test
    void noDebePermitirTransicionInvalida() {
        assertThrows(IllegalStateException.class, () -> {
            solicitud.cerrar("Cierre prematuro", admin);
        });
    }

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

    @Test
    void soloAdministradorPuedeCerrarSolicitud() {
        solicitud.clasificar(new Prioridad("ALTA", "Prioridad para cierre"));
        solicitud.asignarResponsable(admin);
        solicitud.marcarComoAtendida();

        assertThrows(IllegalStateException.class, () -> {
            solicitud.cerrar("Intento de cierre por estudiante", estudiante);
        });
    }

    @Test
    void historialDebeSerInmutableDesdeAfuera() {
        assertThrows(UnsupportedOperationException.class, () -> {
            solicitud.getHistorial().add(new EventoHistorial(null, "Hack", null));
        });
    }
}