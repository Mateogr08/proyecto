package co.edu.uniquindio.proyecto.domain;

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

    @BeforeEach
    void setup() {
        id = new CodigoSolicitud("EST-9876");
        tipo = new TipoSolicitud("Cancelación");
        solicitud = new Solicitud(id, tipo);
    }

    @Test
    void debeRegistrarEventoAlCrearSolicitud() {
        assertEquals(1, solicitud.getHistorial().size());
        assertEquals(EstadoSolicitud.REGISTRADA, solicitud.getHistorial().get(0).estadoRelacionado());
    }

    @Test
    void noDebePermitirTransicionInvalida() {
        assertThrows(IllegalStateException.class, () -> {
            solicitud.cerrar("Cierre prematuro");
        });
    }

    @Test
    void noDebeModificarSolicitudCerrada() {
        solicitud.clasificar(new Prioridad("MEDIA", "Justificación válida de prueba"));

        Usuario responsable = new Usuario("123", "Funcionario Académico", new Email("f@u.co"));
        solicitud.asignarResponsable(responsable);

        solicitud.marcarComoAtendida();

        solicitud.cerrar("Observación final");

        assertThrows(IllegalStateException.class, () -> {
            solicitud.asignarResponsable(responsable);
        });
    }

    @Test
    void historialDebeSerInmutableDesdeAfuera() {
        assertThrows(UnsupportedOperationException.class, () -> {
            solicitud.getHistorial().add(new EventoHistorial(null, "Hack", null));
        });
    }
}